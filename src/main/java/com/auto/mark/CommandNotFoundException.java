package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

/** An exception class used in the case of an invalid command was passed in the command line. */
public class CommandNotFoundException extends GenericMessageException {
	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 4983649253686737822L;

	public CommandNotFoundException(String cmd) {
		super("The command '" + cmd + "' was not found !");
	}
}
