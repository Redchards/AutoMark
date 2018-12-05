package gui.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class EmptyDirectoryException extends GenericMessageException {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 4467549662511125988L;

	public EmptyDirectoryException(String msg) {
		super("Empty directory : " + msg);
	}
	
	public EmptyDirectoryException(String msg, Throwable cause) {
		super("Empty directory : " + msg, cause);
	}
}
