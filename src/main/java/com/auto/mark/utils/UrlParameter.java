package com.auto.mark.utils;

public class UrlParameter {
	private String parameterName;
	private String parameterValue;
	
	public UrlParameter(String parameterName, String parameterValue) {
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}
	
	public UrlParameter(UrlParameter other) {
		this.parameterName = other.parameterName;
		this.parameterValue = other.parameterValue;
	}
	
	public String getParameterName() {
		return parameterName;
	}
	
	public String getParameterValue() {
		return parameterValue;
	}
	
	public String toString() {
		return parameterName + "=" + parameterValue;
	}
}
