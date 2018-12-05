package com.auto.mark.utils;

public class TestDescriptorSerializationException extends GenericMessageException {
	private static final long serialVersionUID = 1L;

	public TestDescriptorSerializationException(String action) {
		super("Error during the test descriptor " + action);
	}

	public TestDescriptorSerializationException(String action, Throwable otherException) {
		super("Error during the test descriptor " + action, otherException);
	}
}
