package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class ReportFailureException extends GenericMessageException {
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 3749925050633821998L;

	public ReportFailureException(String msg) {
		super("Failed to write the report for the current correction : " + msg);
	}
	
	public ReportFailureException(String msg, Throwable cause) {
		super("Failed to write the report for the current correction : " + msg, cause);
	}
}
