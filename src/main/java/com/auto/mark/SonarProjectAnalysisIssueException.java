package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

public class SonarProjectAnalysisIssueException extends GenericMessageException {

	private static final long serialVersionUID = 1L;

	public SonarProjectAnalysisIssueException(String missingIdentifier) {
		super("The JsonObject does not represent a valid SonarQube issue (missing identifier '" + missingIdentifier + "')");
	}

	public SonarProjectAnalysisIssueException(String missingIdentifier, Throwable cause) {
		super("The JsonObject does not represent a valid SonarQube issue (missing identifier '" + missingIdentifier + "')", cause);
	}

}
