package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class InvalidRangeException extends GenericMessageException{

	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -5255178858053929560L;

	public InvalidRangeException(String msg) {
		super("Error during the creation of the range " + msg);
	}
}
