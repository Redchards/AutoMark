package com.auto.mark.runner;

import java.io.Serializable;

/**
 * A very simple classe used to describe the result of a test.
 *
 */
public class TestDescriptor implements Serializable {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 5646707975314887651L;
	
	private String name;
	private TestState state;
	private double value;
	private String additionalMessage;
	
	public TestDescriptor(String name, TestState state, double value, String additionalMessage) {
		this.name = name;
		this.state = state;
		this.value = value;
		this.additionalMessage = additionalMessage;
	}
	
	public TestDescriptor(String name, TestState state, double value) {
		this(name, state, value, "");
	}
	
	public String getName() {
		return name;
	}
	
	public TestState getState() {
		return state;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getAdditionalMessage() {
		return additionalMessage;
	}
}
