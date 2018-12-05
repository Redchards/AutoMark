package com.auto.mark;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.auto.mark.utils.IProjectDescriptor;

/**
 * Simplified class that stores each SonarQube rules issues' occurrences for a specific analyzed project.
 */

public class SonarAnalysisResult implements Iterable<Entry<String, SonarProjectAnalysisIssue>> {

	/**
	 * Map of rules issues that occurred for the specified project and associated with their occurrences.
	 */
	private HashMap<String, SonarProjectAnalysisIssue> issuesOccurrences;

	/**
	 * The project's key.
	 */

	private IProjectDescriptor projectDescriptor;

	/**
	 * Creates an instance with an empty list of issues.
	 * 
	 * @param projectKey The project's key.
	 */

	public SonarAnalysisResult(IProjectDescriptor projectDescriptor) {
		this.projectDescriptor = projectDescriptor;
		this.issuesOccurrences = new HashMap<>();
	}

	/**
	 * @param rule The rule to get the number of occurrences from. If the rule isn't present in the hash, it returns 0
	 *            as the rule issue didn't occur.
	 * @return The number of occurrences for the specified rule
	 * @throws SonarAnalysisResultException
	 */
	public int getOccurrencesFrom(String rule) throws SonarAnalysisResultException {
		if (issuesOccurrences.containsKey(rule)) {
			return issuesOccurrences.get(rule).getNumberOfOccurrences();
		} else {
			String msg = "The rule " + rule + " hasn't occured : number of occurence = 0 \n For project : "
					+ projectDescriptor.getProjectKey();
			throw new SonarAnalysisResultException(msg);
		}
	}

	/**
	 * Adds a specific issue to the list of issues and auto-increments its counter.
	 * 
	 * @param issue The issue to add
	 */

	public void addIssue(SonarProjectAnalysisIssue issue) {

		if (!issuesOccurrences.containsKey(issue.getRule())) {
			issue.incrementNumberOfOccurrences();
			issuesOccurrences.put(issue.getRule(), issue);
		} else {
			issuesOccurrences.get(issue.getRule()).incrementNumberOfOccurrences();
		}
	}
	
	/**
	 * @return wether analysis result has some issues.
	 */
	public boolean hasIssue() {
		return !issuesOccurrences.isEmpty();
	}

	/**
	 * @return A simple String that describes every rules issues that occurred and their occurrences.
	 */
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		Set<Map.Entry<String, SonarProjectAnalysisIssue>> entrySet = issuesOccurrences.entrySet();
		Iterator<Entry<String, SonarProjectAnalysisIssue>> it = entrySet.iterator();

		while (it.hasNext()) {
			Entry<String, SonarProjectAnalysisIssue> entry = it.next();
			sb.append("Rule '" + entry.getKey() + "' occured : " + entry.getValue() + " time(s)\n");
		}

		return sb.toString();
	}

	/**
	 * @return an iterator on the underlying container.
	 */
	@Override
	public Iterator<Entry<String, SonarProjectAnalysisIssue>> iterator() {
		return issuesOccurrences.entrySet().iterator();
	}
}
