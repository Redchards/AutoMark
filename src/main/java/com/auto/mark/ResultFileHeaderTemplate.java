package com.auto.mark;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The result CSV file header template.
 * @see ResultReporter
 */
public class ResultFileHeaderTemplate extends CsvHeaderTemplate {
	private static final String PROJECT_ID_HEADER_LABEL = "projectId";
	private static final String COMPILATION_STATUS_HEADER_LABEL = "status";
	private static final String FACTOR_HEADER_LABEL = "factor";
	private static final String FINAL_RESULT_HEADER_LABEL = "finalResult";
	
	/**
	 * Build the header template.
	 * 
	 * @param markingMethods The test methods used to mark the project.
	 */
	public ResultFileHeaderTemplate(Set<String> markingMethodNames) {
		super();
		
		List<String> header = new ArrayList<>();
		header.add(PROJECT_ID_HEADER_LABEL);
		header.add(COMPILATION_STATUS_HEADER_LABEL);
		
		for(String methodName : markingMethodNames) {
			header.add(methodName);
		}
		
		header.add(FACTOR_HEADER_LABEL);
		header.add(FINAL_RESULT_HEADER_LABEL);
		
		super.setTemplate(header);
	}
}
