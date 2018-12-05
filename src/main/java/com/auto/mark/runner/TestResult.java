package com.auto.mark.runner;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.auto.mark.annotation.MarkedTest;

/**
 * A class used to represent the results of a sequence of tests.
 */
public class TestResult implements Iterable<TestDescriptor>, Serializable {

	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = 3627461313810247443L;

	/**
	 * The maximum total of points that can be obtained for this sequence of tests.
	 */
	private float totalObtainable;

	/**
	 * The total of points obtained for this sequence of tests.
	 */
	private float pointsObtained;

	/**
	 * A HashMap that contains tests' descriptions.
	 */
	private HashMap<String, TestDescriptor> markedTests;

	public TestResult() {
		this.markedTests = new HashMap<>();
	}

	/**
	 * Adds the results of a test.
	 * 
	 * @param method The test that has being executed
	 * @param state Indicates if the test passed or failed.
	 * @param additionalMessage A commentary that gives further information about this test
	 */
	public void addResult(Method method, TestState state, String additionalMessage) {
		MarkedTest test = (MarkedTest) method.getAnnotation(MarkedTest.class);
		System.out.println(state);
		TestDescriptor testDescriptor = new TestDescriptor(method.getName(), state, test.value(), additionalMessage);
		markedTests.put(method.getName(), testDescriptor);

		if (state == TestState.PASSED) {
			pointsObtained += test.value();
		}

		totalObtainable += test.value();
	}

	/**
	 * Adds the results of a test.
	 * 
	 * @param method The test that has being executed
	 * @param state Indicates if the test passed or failed.
	 */
	public void addResult(Method method, TestState state) {
		addResult(method, state, "");
	}

	/**
	 * @return The maximum total of points that can be obtained for this sequence of tests.
	 */

	public float getTotalObtainable() {
		return totalObtainable;
	}

	/**
	 * @return The total of points obtained for this sequence of tests.
	 */
	public float getPointsObtained() {
		return pointsObtained;
	}
	
	public Set<String> getMethodNames() {
		return markedTests.keySet();
	}

	/**
	 * @return An iterator on TestDescriptor objects that represents the tests that has been executed
	 *         and their results.
	 */
	public Iterator<TestDescriptor> iterator() {
		return markedTests.values().iterator();
	}
}
