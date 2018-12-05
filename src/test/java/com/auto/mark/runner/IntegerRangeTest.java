package com.auto.mark.runner;

import org.junit.Test;

import com.auto.mark.IntegerRange;
import com.auto.mark.InvalidRangeException;

public class IntegerRangeTest {
	
	@Test(expected = InvalidRangeException.class)
	public void lowerNegatif() throws InvalidRangeException {
		new IntegerRange(-1,3);
	}
	
	@Test(expected = InvalidRangeException.class)
	public void upperNegatif() throws InvalidRangeException {
		new IntegerRange(1,-3);
	}
	
	@Test(expected = InvalidRangeException.class)
	public void lowerBiggerThanUpper() throws InvalidRangeException {
		new IntegerRange(6,3);
	}
}
