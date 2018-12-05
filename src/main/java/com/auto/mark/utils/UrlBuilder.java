package com.auto.mark.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides a convenient way of using and creating URL.
 * The URL's parameters might be considered as instances of UrlParameter class.
 * A parameter can either be added as UrlParameter object or simply with its name and its value.
 */
public class UrlBuilder {
	
	/**
	 * The list of parameters to use for the given URL of this instance.
	 */
	List<UrlParameter> parameterList;
	
	/**
	 * The base of the URL that'll likely be appended. It generally represents a domain name.
	 */
	String baseUrl;
	
	/**
	 * The path to use for this URL that'll append the base URL.
	 */
	String path;
	
	/**
	 * Creates an simple instance with an empty base URL and no parameter.
	 */
	public UrlBuilder() {
		this("");
	}
	
	/**
	 * Creates an instance with a given base URL and no parameter.
	 * @param baseUrl The given base URL
	 */
	
	public UrlBuilder(String baseUrl) {
		this(baseUrl, new ArrayList<>());
	}
	
	/**
	 * Creates an instance with a given base URL and with a list of parameters.
	 * @param baseUrl The given base URL
	 * @param params The given list of parameters
	 */
	
	public UrlBuilder(String baseUrl, UrlParameter ... params) {
		this(baseUrl, Arrays.asList(params));
	}
	
	/**
	 * Creates an instance with a given base URL and with a list of parameters.
	 * @param baseUrl The given base URL
	 * @param params The given list of parameters
	 */
	
	public UrlBuilder(String baseUrl, List<UrlParameter> params) {
		this.baseUrl = baseUrl;
		parameterList = new ArrayList<>(params);
		this.path = "";
	}
	
	/**
	 * Appends a path to the current path.
	 * @param path The path to add
	 */

	public void appendPath(String path) {
		this.path += "/" + path;
	}

	/**
	 * Clears the current path.
	 */
	public void clearPath() {
		path = "";
	}

	/**
	 * 
	 * @return The current path.
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets the base URL.
	 * @param baseUrl The base URL
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * 
	 * @return The base URL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * Returns the UrlParameter instance at the given instance of this URL.
	 * @param index The index of the UrlParameter instance
	 * @return the UrlParameter instance at the given index
	 */
	
	public UrlParameter getParameter(int index) {
		return parameterList.get(index);
	}
	
	/**
	 * Sets the UrlParameter instance at the given index of this URL.
	 * @param index The index to set the UrlParameter to
	 * @param parameterName The parameter's name
	 * @param parameterValue The parameter's value
	 */
	public void setParameter(int index, String parameterName, String parameterValue) {
		setParameter(index, new UrlParameter(parameterName, parameterValue));
	}
	
	/**
	 * Sets the UrlParameter instance at the given index of this URL.
	 * @param index The index to set the UrlParameter to
	 * @param value The UrlParameter instance
	 */
	
	public void setParameter(int index, UrlParameter value) {
		parameterList.set(index, value);
	}
	
	/**
	 * Adds a parameter to the URL.
	 * @param parameterName The parameter's name
	 * @param parameterValue The parameter's value
	 */
	
	public void add(String parameterName, String parameterValue) {
		add(new UrlParameter(parameterName, parameterValue));
	}
	
	/**
	 * Adds a parameter to the URL with a given UrlParameter instance.
	 * @param param The given UrlParameter instance
	 */
	
	public void add(UrlParameter param) {
		parameterList.add(param);
	}
	
	/**
	 * Adds multiple parameters to the URL with given UrlParameter instances.
	 * @param params The UrlParameter instances to add
	 */
	public void addAll(UrlParameter ... params) {
		addAll(Arrays.asList(params));
	}
	
	/**
	 * Adds multiple parameters to the URL from a given list of UrlParameter instances.
	 * @param params The list of UrlParameter instances.
	 */
	public void addAll(List<UrlParameter> params) {
		parameterList.addAll(params);
	}
	
	/**
	 * @return A string that describes this URL. <br>
	 * An example, <br>
	 * - base url = "helloworld.com" <br>
	 * - path = "request"
	 * - parameters : commit=yes, show=no; <br>
	 * returns "http://helloworld.com/request/commit=yes&show=no"
	 */
	public String toString() {
		String base = baseUrl + path;

		if(parameterList.isEmpty()) {
			return base;
		}
		else {
			
			StringBuilder builder = new StringBuilder(base);
			builder.append("?");
			
			for(int i = 0; i < parameterList.size() - 1; i++) {
				builder.append(parameterList.get(i).toString());
				builder.append("&");
			}
			
			builder.append(parameterList.get(parameterList.size() - 1));
			
			return builder.toString();
		}
	}
}
