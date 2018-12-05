package com.auto.mark.utils;


public class MavenDescriptorException extends GenericMessageException {

	private static final long serialVersionUID = -7625333064836262110L;

	public MavenDescriptorException(String projectDirectory, String msg) {

		super("(" + projectDirectory + ")" + "Error when fetching the project details : " + msg);

	}

	public MavenDescriptorException(String projectDirectory, String msg, Throwable cause) {

		super("(" + projectDirectory + ")" + "Error when fetching the project details : " + msg, cause);

	}

}
