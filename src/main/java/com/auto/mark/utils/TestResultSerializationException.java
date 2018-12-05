package com.auto.mark.utils;

public class TestResultSerializationException extends GenericMessageException {

	private static final long serialVersionUID = 1L;
	

	public TestResultSerializationException(String action) {
		super("Error during the test result " + action);
	}

	public TestResultSerializationException(String action, Throwable otherException) {
		super("Error during the test result " + action, otherException);
	}

}