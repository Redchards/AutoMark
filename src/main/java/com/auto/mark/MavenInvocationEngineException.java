package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

/**
 * An exception type used by {@link MavenInvocationEngine}.
 */
public class MavenInvocationEngineException extends GenericMessageException {
	/**
	 * Generated serial UID.
	 */
	private static final long serialVersionUID = -3952671778011475857L;

	public MavenInvocationEngineException(String msg) {
		super("Error in the Maven Engine execution : " + msg);
	}
	
	public MavenInvocationEngineException(String msg, Throwable cause) {
		super("Error in the Maven Engine execution : " + msg, cause);
	}
}