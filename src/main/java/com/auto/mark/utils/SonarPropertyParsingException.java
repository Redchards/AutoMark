package com.auto.mark.utils;

public class SonarPropertyParsingException extends GenericMessageException {

	private static final long serialVersionUID = -1191803335495133787L;

	public SonarPropertyParsingException(String msg) {
		super("Failed to parse the sonar property file : " + msg);
	}

	public SonarPropertyParsingException(Throwable cause) {
		super("Failed to parse the sonar property file", cause);
	}

	public SonarPropertyParsingException(String msg, Throwable cause) {
		super("Failed to parse the sonar property file : " + msg, cause);
	}
}
