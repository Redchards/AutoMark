package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class SonarAnalysisException extends GenericMessageException {

	private static final long serialVersionUID = 1L;
	

	public SonarAnalysisException(String msg) {
		super(msg);
	}

	public SonarAnalysisException(String msg, Throwable otherException) {
		super(msg, otherException);
	}

}
