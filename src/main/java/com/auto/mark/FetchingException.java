package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

/**
 * An exception type used by {@link FetcherInterface}
 * @see FetcherInterface
 * @see LocalDirectoryFetcher
 */
public class FetchingException extends GenericMessageException {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -9030723337260232704L;

	/**
	 * @param msg The message of the exception.
	 */
	public FetchingException(String msg) {
		super("Error during the projects fetching : " + msg);
	}
	
	/**
	 * @param msg The message of the exception.
	 */
	public FetchingException(String msg, Throwable cause) {
		super("Error during the projects fetching : " + msg, cause);
	}
}
