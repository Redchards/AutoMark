package com.auto.mark;

import java.util.HashMap;

/**
 * This class represents the marking configuration used for generating the code quality factor
 * in order mark analyzed projects. The marking configuration is based on SonarQube rules activation.
 * A SonarQube rule is described by a SonarRule class instance.
 */

public class MarkingConfiguration {

	/**
	 * The HashMap contains every SonarQube rule activated from the marking configuration indexed
	 * by the rule's.
	 */
	private HashMap<String, SonarRule> hashMap;

	/**
	 * The minimum factor that can be applied to the project marking concerning the code quality.
	 */
	private double minFactor;

	public MarkingConfiguration() {
		this.hashMap = new HashMap<>();
	}

	/**
	 * Sets the minimum factor that can be applied to project marking concerning the code quality.
	 * 
	 * @param minFactor The minimum factor
	 */

	public void setMinFactor(double minFactor) {
		
		if (minFactor < 0) {
			this.minFactor = 0;

		} else if (minFactor > 1) {
			this.minFactor = 1;
		} else {
			this.minFactor = minFactor;
		}

	}
	
	/**
	 * 
	 * @return The minimum factor that can be applied to the project marking concerning the code quality
	 */

	public double getMinFactor() {
		return minFactor;
	}
	
	/**
	 * Checks if a rule is present in the marking configuration.
	 * @param ruleId The rule's ID
	 * @return True if a rule is present. Otherwise, returns false.
	 */

	public boolean containsRule(String ruleId) {
		return hashMap.containsKey(ruleId);
	}

	/**
	 * Adds a SonarRule to the marking configuration
	 * @param ruleId The rule's ID
	 * @param rule The SonarRule to add
	 */
	public void addRule(String ruleId, SonarRule rule) {
		hashMap.put(ruleId, rule);
	}

	
	/**
	 * Returns a SonarRule object that describes the given SonarQube rule ID.
	 * @param ruleId The rule ID
	 * @return The SonarRule instance
	 */
	public SonarRule getRule(String ruleId) {
		return hashMap.get(ruleId);
	}
	
	/**
	 * Returns a string that describes every SonarQube rule activated by this marking configuration.
	 * Each rule ID is concatenated to each other and they are separated by commas.
	 * It's a convenient method to call in order to build lines in CSV files.
	 * @return A string that represents rules as lines for CSV files with comma separator
	 */

	public String getRulesAsCSV() {
		StringBuilder builder = new StringBuilder();

		for (String ruleName : hashMap.keySet()) {
			builder.append(ruleName).append(",");
		}

		return builder.toString().substring(0, builder.length() - 1);
	}
}
