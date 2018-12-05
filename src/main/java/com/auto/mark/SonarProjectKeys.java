package com.auto.mark;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;

/**
 * This class is used to retrieve key projects of projects that have been analyzed by a specific SonarQube server.
 * Its main purpose is to make SonarProjectAnalysis class' usage easier.
 * 
 * @author Samson
 *
 */
public class SonarProjectKeys {

	/**
	 * List of key project that has the same specific project's name substring.
	 */
	private List<String> keyList;

	/**
	 * The url used for the SonarQube's server web api.
	 */

	private String urlRequest;

	// TODO need to replace by the future configuration file argument
	public final static String DEFAULT_SERVER_ADDRESS = "localhost:9000";

	private final static String DEFAULT_PATH_TO_PROJECT_INDEX = "api/projects";

	private final static String DEFAULT_REQUEST = "index?format=json";

	private final static String DEFAULT_ARGUMENT_PROJECT_KEY = "k";

	/**
	 * Creates a connection to the specified SonarQube server and retrieves key project that has the same specific
	 * project's name substring.
	 * 
	 * @param projectName The projects substring used to search for projects.
	 * @param serverAddress The SonarQube server address.
	 * @throws SonarAnalysisException If any problem occurs during the connection to the server.
	 */
	public SonarProjectKeys(String projectName, String serverAddress) throws SonarAnalysisException {

		keyList = new ArrayList<>();
		StringBuilder sb = new StringBuilder("http://");
		sb.append(serverAddress + "/").append(DEFAULT_PATH_TO_PROJECT_INDEX + "/").append(DEFAULT_REQUEST + "");
		sb.append("&search=" + projectName);
		urlRequest = sb.toString(); // http://localhost:9000/api/projects/index?format=json&search=cadrage

		URL url;
		HttpURLConnection connection = null;
		
		try {
			url = new URL(urlRequest);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
		} catch (MalformedURLException e) {
			throw new SonarAnalysisException("error when connecting to the SonarQube server : malformed URL", e);
		} catch (ProtocolException e) {
			throw new SonarAnalysisException("error when connecting to the SonarQube server : bad protocol", e);
		} catch (IOException e) {
			throw new SonarAnalysisException("error when connecting to the SonarQube server", e);
		}

		
		try(BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			JsonReader reader = Json.createReader(in);) {

			JsonArray jsonProjects = reader.readArray();

			if (jsonProjects.isEmpty()) {
				throw new SonarAnalysisException("There isn't any project analysis to retrieve.");
			}

			else {
				for (int i = 0; i < jsonProjects.size(); i++) {
					keyList.add(jsonProjects.getJsonObject(i).getString(DEFAULT_ARGUMENT_PROJECT_KEY));
				}

			}

		} catch (MalformedURLException e) {
			throw new SonarAnalysisException(
					urlRequest + "The URL is incorrect, please check the correctness of your SonarQube server address.",
					e);
		} catch (ProtocolException e) {
			throw new SonarAnalysisException("There might be a TCP error with your connection to SonarQube server.", e);
		} catch (IOException e) {
			throw new SonarAnalysisException(
					"Couldn't build a stream to SonarQube server in order to search for projects.", e);
		}

	}

	/**
	 * 
	 * @return The list of key project.
	 */
	public List<String> getKeyList() {
		return keyList;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyList.size(); i++) {
			sb.append(keyList.get(i) + "\n");
		}
		return sb.toString();
	}

}
