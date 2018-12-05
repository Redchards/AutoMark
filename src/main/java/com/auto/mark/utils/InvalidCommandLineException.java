package com.auto.mark.utils;

public class InvalidCommandLineException extends GenericMessageException {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 120761124049688773L;

	public InvalidCommandLineException(String msg) {
		super(msg + " Use --help to get more informations.");
	}
}
