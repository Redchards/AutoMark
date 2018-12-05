package com.auto.mark;

import java.util.Map.Entry;

/**
 * Utility class focused on the generation of a quality factor used to modulate the
 * final analyzed project's mark given a {@link MarkingConfiguration} and a
 * {@link SonarAnalysisResult}.
 * 
 * @see ResultReporter
 */
public class CodeQualityFactorGenerator {

	/**
	 * Generate a double factor from 1.0 to the minimal factor from a MarkingConfiguration instance and a SonarAnalysisResult instance
	 * in order to grade the specific project related to the SonarAnalysisResult.
	 * 
	 * @param markingConfiguration The MarkingConfiguration instance that describes the marking configuration to use.
	 * @param sonarAnalysisResult The SonarAnalysisResult instance that describes the analyzed project's results to use.
	 * 
	 * @return A code quality factor between 0.0 and 1.0
	 */
	public static double generate(MarkingConfiguration markingConfiguration, SonarAnalysisResult sonarAnalysisResult) {

		double factor = 1.0;

		for (Entry<String, SonarProjectAnalysisIssue> entry : sonarAnalysisResult) {
			String ruleName = entry.getKey();
			Integer nbOccurrences = entry.getValue().getNumberOfOccurrences();

			if(markingConfiguration.containsRule(ruleName)) {
				factor -= markingConfiguration.getRule(ruleName).getSubstractedValueFromOccurrence(nbOccurrences);
			}

		}

		if (factor < markingConfiguration.getMinFactor()) {
			factor = markingConfiguration.getMinFactor();
		}

		return factor;
	}

}
