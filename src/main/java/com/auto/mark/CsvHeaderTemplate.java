package com.auto.mark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * A class used to describe a parametrizable CSV header.
 * 
 * @see ResultFileHeaderTemplate, DetailsFileHeaderTemplate
 */
public class CsvHeaderTemplate implements Iterable<String> {
	private List<String> header;
	
	public CsvHeaderTemplate(List<String> header) {
		setTemplate(header);
	}
	
	public CsvHeaderTemplate(String... header) {
		this(Arrays.asList(header));
	}
	
	public CsvHeaderTemplate(CsvHeaderTemplate other) {
		setTemplate(other);
	}
	
	public CsvHeaderTemplate() {
		this.header = new ArrayList<>();
	}
	
	public void setTemplate(List<String> header) {
		this.header = new ArrayList<>(header);
	}
	
	public void setTemplate(CsvHeaderTemplate other) {
		this.header = other.header;
	}

	@Override
	public Iterator<String> iterator() {
		return header.iterator();
	}
}
