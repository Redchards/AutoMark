package cmd.auto.mark;

import com.auto.mark.utils.GenericMessageException;

/**
 * The exception used by the textual progress handlers.
 * @see TextualProgressHandler
 * @see FancyTextualProgressHandler
 */
public class TextualProgressHandlerException extends GenericMessageException {

	private static final long serialVersionUID = 7396004672229844946L;

	public TextualProgressHandlerException(Throwable e) {
		super("Error in the progress handler", e);
	}
}
