package com.auto.mark.runner;

import org.junit.Test;

import com.auto.mark.DoubleRange;
import com.auto.mark.InvalidRangeException;

public class DoubleRangeTest {
	
	@Test(expected = InvalidRangeException.class)
	public void lowerNegatif() throws InvalidRangeException {
		new DoubleRange(-1.1,3.5);
	}
	
	@Test(expected = InvalidRangeException.class)
	public void upperNegatif() throws InvalidRangeException {
		new DoubleRange(1.1,-3.5);
	}
	
	@Test(expected = InvalidRangeException.class)
	public void lowerBiggerThanUpper() throws InvalidRangeException {
		new DoubleRange(6.6,3.5);
	}
}
