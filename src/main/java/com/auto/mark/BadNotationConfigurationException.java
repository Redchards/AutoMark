package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

/**
 * Exception type used by {@link MarkingConfigurationParser}.
 */
public class BadNotationConfigurationException extends GenericMessageException {
	
	/**
	 * Generated IUD.
	 */
	private static final long serialVersionUID = -2054479195917690567L;

	public BadNotationConfigurationException(String msg, int lineNo) {
		super("Error during the notation configuration file parsing at line " + lineNo + " : " + msg);
	}
	
	public BadNotationConfigurationException(String msg) {
		super("Error during the notation configuration file parsing : " + msg);
	}
	
	public BadNotationConfigurationException(String msg, int lineNo, Throwable e) {
		super("Error during the notation configuration file parsing at line " + lineNo + " : " + msg, e);
	}
	
	public BadNotationConfigurationException(String msg, Throwable e) {
		super("Error during the notation configuration file parsing : " + msg, e);
	}

}
