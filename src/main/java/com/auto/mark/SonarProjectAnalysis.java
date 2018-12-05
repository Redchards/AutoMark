package com.auto.mark;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.IProjectDescriptor;
import com.auto.mark.utils.PathBuilder;
import com.auto.mark.utils.SonarPropertyParser;
import com.auto.mark.utils.SonarPropertyParsingException;
import com.auto.mark.utils.UrlBuilder;

/**
 * This class is used to retrieve every rules issues (code quality) for a project that has been analyzed
 * by a SonarQube server.
 * 
 * @author Samson
 */
public class SonarProjectAnalysis {

	private static final String SONAR_API_ISSUES_PATH = "api/issues/search";
	
	private static final String PROJECT_KEY_PARAMETER = "projectKeys";
	
	private static final String ENABLED_RULES_PARAMETER = "rules";

	private static final String OPEN_ISSUE_STATUS_IDENTIFIER = "OPEN";
	
	/**
	 * List of issues concerning SonarQube rules that occurred during the project's analysis.
	 */
	private SonarAnalysisResult analysisResult;

	/**
	 * The url used for the SonarQube's server web API.
	 */

	private String urlRequest;

	/**
	 * The MavenProjectDescriptor instance that contains relative informations about the analyzed project.
	 */
	private IProjectDescriptor projectDescriptor;

	/**
	 * Basic constructor that'll retrieve issues concerning a project by using its key project from
	 * a specific SonarQube server.
	 * TODO : Make use of HTTP library from apache.
	 * TODO : We're assuming that the report will be available immediately. This could not be the case !!!
	 * https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.5.3
	 * 
	 * @param projectDescriptor The ProjectDescriptor instance that contains informations about an analyzed project such as its project key and its author.
	 * @throws SonarAnalysisException If any problem concerning connection to SonarQube server occurred.
	 * @throws SonarProjectAnalysisIssueException If an issue's informations can't be retrieved with specific keys from.
	 *             a JsonObject that describes the rule issue
	 * @throws SonarPropertyParsingException
	 */

	public SonarProjectAnalysis(IProjectDescriptor projectDescriptor, MarkingConfiguration configuration, File sonarPropertiesFile)
			throws SonarAnalysisException, SonarProjectAnalysisIssueException, SonarPropertyParsingException {
		this.projectDescriptor = projectDescriptor;

		analysisResult = new SonarAnalysisResult(projectDescriptor);

		/* WARNING ! Using only the default property name for now */
		String sonarPropertiesFileName = sonarPropertiesFile.toString();

		Properties sonarProperties = SonarPropertyParser
				.parse(new PathBuilder(sonarPropertiesFileName).toString());
		
		String baseUrl = sonarProperties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyUrlLabel());
		if(baseUrl.endsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.length()-1);
		}
		UrlBuilder builder = new UrlBuilder(baseUrl);
		builder.appendPath(SONAR_API_ISSUES_PATH);
		builder.add(PROJECT_KEY_PARAMETER, projectDescriptor.getProjectKey());
		builder.add(ENABLED_RULES_PARAMETER, configuration.getRulesAsCSV());
		
		urlRequest = builder.toString();
		
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
			JsonReader reader = Json.createReader(in)) {
			

			JsonObject projectReport = reader.readObject();
			JsonArray projectIssuesReport = projectReport.getJsonArray("issues");

			/*
			 * The JsonObject returned by SonarQube server is like :
			 * {
			 * "total":9,
			 * "p":1,
			 * "ps":100, // some values
			 * "paging":{},
			 * "issues":[{..},{..}], <== what we are looking for
			 * "components":
			 * }
			 */

			if (!projectIssuesReport.isEmpty()) {
				for (int i = 0; i < projectIssuesReport.size(); i++) {
					SonarProjectAnalysisIssue issue = new SonarProjectAnalysisIssue(projectIssuesReport.getJsonObject(i));
					
					if(OPEN_ISSUE_STATUS_IDENTIFIER.equals(issue.getStatus())) {
						analysisResult.addIssue(issue);
					}

					/*
					 * an example of a JsonObject that describes one of the issues returned by the issues array :
					 * "key":"AVqeifoUUtQdFq4yB3AT",
					 * "rule":"squid:S1481",
					 * "severity":"MINOR",
					 * "component":"upd.mll5u2o:dupond001.iy001.cadrage:src/main/java/cadrage/lololo/TestingLol.java",
					 * "componentId":70,
					 * "project":"upd.mll5u2o:dupond001.iy001.cadrage",
					 * "textRange":{},
					 * "flows":[],
					 * "resolution":"FIXED",
					 * "status":"CLOSED",
					 * "message":"Remove this unused \"k\" local variable.",
					 * "effort":"5min",
					 * "debt":"5min",
					 * "author":"",
					 * "tags":[],
					 * "creationDate":"2017-03-05T13:55:23+0100",
					 * "updateDate":"2017-03-05T15:59:28+0100",
					 * "closeDate":"2017-03-05T15:59:28+0100",
					 * "type":"CODE_SMELL"
					 */
				}

			}

		} catch (MalformedURLException e) {
			throw new SonarAnalysisException(
					urlRequest + "The URL is incorrect, please check the correctness of your SonarQube server address.",
					e);
		} catch (ProtocolException e) {
			throw new SonarAnalysisException("It might be a TCP error with your connection to SonarQube server", e);
		} catch (IOException e) {
			throw new SonarAnalysisException(
					"Couldn't build a stream to SonarQube server in order to search for projects.", e);
		}

	}
	
	public SonarProjectAnalysis(IProjectDescriptor projectDescriptor, MarkingConfiguration configuration, String sonarPropertiesFileName) 
			throws SonarAnalysisException, SonarProjectAnalysisIssueException, SonarPropertyParsingException {
		this(projectDescriptor, configuration, new File(sonarPropertiesFileName));
	}
	
	/**
	 * @return The sonar analysis's result.
	 */

	public SonarAnalysisResult getAnalysisResults() {
		return analysisResult;
	}

	/**
	 * 
	 * @return The author of the analyzed project.
	 */
	public String getProjectAuthor() {
		return projectDescriptor.getAuthor();
	}
	
	/**
	 * 
	 * @return True if this project analysis contains SonarQube rule issues, otherwise returns false.
	 */
	public boolean hasIssues(){
		return analysisResult.hasIssue();
	}

}
