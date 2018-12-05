package com.auto.mark;

/**
 * A class representing the details CSV file header template.
 * This class is currently not parameterizable.
 */
public class DetailsFileHeaderTemplate extends CsvHeaderTemplate {
	private static final String ID_HEADER_LABEL = "ID";
	private static final String DESCRIPTION_HEADER_LABEL = "description";
	private static final String OCCURRENCES_HEADER_LABEL = "occurrences";
	
	/**
	 * Create the header template.
	 */
	public DetailsFileHeaderTemplate() {
		super(ID_HEADER_LABEL, DESCRIPTION_HEADER_LABEL, OCCURRENCES_HEADER_LABEL);
	}
}
