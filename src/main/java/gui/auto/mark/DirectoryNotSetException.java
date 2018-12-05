package gui.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class DirectoryNotSetException extends GenericMessageException {

	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -5602087705759671032L;

	public DirectoryNotSetException(String directoryType) {
		super("The directory for '" + directoryType + "' was not set !");
	}
	
	public DirectoryNotSetException(String directoryType, Throwable cause) {
		super("The directory for '" + directoryType + "' was not set !", cause);
	}
	
}
