package com.auto.mark;

import com.auto.mark.utils.GenericMessageException;

/**
 * This exception is thrown for SonarAnalysisResult exception when : </br>
 * - an invalid rule issues' occurrence map is used as an argument for the associated constructor ;</br>
 * - an attempt to get a rule's number of occurrences when it's not present.
 * 
 * @author Samson
 *
 */
public class SonarAnalysisResultException extends GenericMessageException {

	private static final long serialVersionUID = 1L;

	public SonarAnalysisResultException(String ruleName) {
		super("The rule " + ruleName + " is invalid.");
	}

	public SonarAnalysisResultException(String ruleName, Throwable otherException) {
		super("The rule " + ruleName + " is invalid.", otherException);
	}

	public SonarAnalysisResultException(String ruleName, int nbOccurrence) {
		super("The number of occurences for " + ruleName + " is invalid : " + nbOccurrence + " <=0 ");
	}

	public SonarAnalysisResultException(String ruleName, int nbOccurrence, Throwable otherException) {
		super("The number of occurences for " + ruleName + " is invalid : " + nbOccurrence + " <=0 ", otherException);
	}

}
