package com.auto.mark;

import java.util.HashMap;

import javax.json.JsonObject;

/**
 * This class describes a SonarQube rule issue that occurred for a specific analyzed project.
 * 
 * @author Samson
 *
 */

class SonarProjectAnalysisIssue {
	
	private static final String PROJECT_KEY_IDENTIFIER = "project";
	private static final String RULE_NAME_IDENTIFIER = "rule";
	private static final String ISSUE_KEY_IDENTIFIER = "key";
	private static final String DESCRIPTION_IDENTIFIER = "message";
	private static final String ISSUE_STATUS_IDENTIFIER = "status";
	
	private static final String[] idArray = {
		PROJECT_KEY_IDENTIFIER,
		RULE_NAME_IDENTIFIER,
		ISSUE_KEY_IDENTIFIER,
		DESCRIPTION_IDENTIFIER,
		ISSUE_STATUS_IDENTIFIER
	};
	
	/**
	 * The map containing the issue informations.
	 */
	private HashMap<String, String> infoMap;
	
	private int occurrences;

	/**
	 * Creates an instance with informations from a JsonObject that describes the issue.
	 * It's useful especially for retrieving data using the SonarQube web api since its return is in json format.
	 * 
	 * @param issueInformation The JsonObject that describes the rule issue.
	 * @throws SonarProjectAnalysisIssueException If the JsonObject doesn't describe a SonarQube rule issue with specific keys.
	 */
	public SonarProjectAnalysisIssue(JsonObject issueInformation) throws SonarProjectAnalysisIssueException {
		infoMap = new HashMap<>();
		
		for(String id : idArray) {
			if(issueInformation.containsKey(id)) {
				infoMap.put(id, issueInformation.getString(id));
			}
			else {
				throw new SonarProjectAnalysisIssueException(id);
			}
		}
		
		occurrences = 0;
		
	}

	public String getProjectKey() {
		return infoMap.get(PROJECT_KEY_IDENTIFIER);
	}

	public String getRule() {
		return infoMap.get(RULE_NAME_IDENTIFIER);
	}

	public String getIssueKey() {
		return infoMap.get(ISSUE_KEY_IDENTIFIER);
	}
	
	public String getDescription() {
		return infoMap.get(DESCRIPTION_IDENTIFIER);
	}
	
	public String getStatus() {
		return infoMap.get(ISSUE_STATUS_IDENTIFIER);
	}
	
	public void incrementNumberOfOccurrences() {
		occurrences++;
	}
	
	public void decrementNumberOfOccurrences() {
		if(occurrences >= 1) {
			occurrences--;
		}
	}
	
	public int getNumberOfOccurrences() {
		return occurrences;
	}
}
