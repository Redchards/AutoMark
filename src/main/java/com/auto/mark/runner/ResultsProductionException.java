package com.auto.mark.runner;

import com.auto.mark.utils.GenericMessageException;

public class ResultsProductionException extends GenericMessageException {

	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -5247582531999435750L;

	public ResultsProductionException(String msg) {
		super("Error when producing the results : " + msg);
	}
	
	public ResultsProductionException(String msg, Throwable cause) {
		super("Error when producing the results : " + msg, cause);
	}
	
}
