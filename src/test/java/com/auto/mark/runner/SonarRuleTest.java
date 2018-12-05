package com.auto.mark.runner;

import org.junit.Assert;
import org.junit.Test;

import com.auto.mark.DoubleRange;
import com.auto.mark.IntegerRange;
import com.auto.mark.InvalidRangeException;
import com.auto.mark.SonarRule;



public class SonarRuleTest {
	
	@Test
	public void rightRuleCode() throws InvalidRangeException{
		SonarRule rule = new SonarRule("S1118", new DoubleRange(0.05,0.1), new IntegerRange(2,4));
		Assert.assertEquals("S1118", rule.getRuleCode());
	}
	
	@Test
	public void getSubstractedValueFromOccurrenceTest1() throws InvalidRangeException{
		SonarRule rule = new SonarRule("S1118", new DoubleRange(0.05,0.1), new IntegerRange(2,4));
		Assert.assertEquals(0.075, rule.getSubstractedValueFromOccurrence(3),0.0000001);
	}
	
	@Test
	public void getSubstractedValueFromOccurrenceTest2() throws InvalidRangeException{
		SonarRule rule = new SonarRule("S1118", new DoubleRange(0.05,0.1), new IntegerRange(2,4));
		Assert.assertEquals(0.05, rule.getSubstractedValueFromOccurrence(2),0.0000001);
	}
	
	@Test
	public void getSubstractedValueFromOccurrenceTest3() throws InvalidRangeException{
		SonarRule rule = new SonarRule("S1118", new DoubleRange(0.05,0.1), new IntegerRange(2,4));
		Assert.assertEquals(0.1, rule.getSubstractedValueFromOccurrence(4),0.0000001);
	}
	
	@Test
	public void getSubstractedValueFromOccurrenceTest4() throws InvalidRangeException{
		SonarRule rule = new SonarRule("S1118", new DoubleRange(0.05,0.1), new IntegerRange(2,4));
		Assert.assertEquals(0.1, rule.getSubstractedValueFromOccurrence(10),0.0000001);
	}
	
	@Test
	public void getSubstractedValueFromOccurrenceTest5() throws InvalidRangeException{
		SonarRule rule = new SonarRule("S1118", new DoubleRange(0.05,0.1), new IntegerRange(2,4));
		Assert.assertEquals(0, rule.getSubstractedValueFromOccurrence(1),0.0000001);
	}

}
