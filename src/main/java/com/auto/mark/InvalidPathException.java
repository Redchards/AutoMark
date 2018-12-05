package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class InvalidPathException extends GenericMessageException {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -6130509432347008805L;

	public InvalidPathException(String path) {
		super("The path '" + path + "' is unreachable. Please check the spelling and the existence of this directory !");
	}
}
