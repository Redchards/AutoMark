package com.auto.mark;

/**
 * 
 * @author Bastien
 *	This class represents a SonarQube rule.
 */
public class SonarRule {
	
	private String ruleCode;
	private DoubleRange doubleRange;
	private IntegerRange integerRange;
	private String modifier;
	
	/**
	 * 
	 * @param ruleCode This is the ID of the SonarQube's error.
	 * @param valueMin	This is the minimal effect on the code quality factor.
	 * @param valueMax	This is the maximal effect on the code quality factor.
	 * @param nbMin	This is the minimal number of repetition to affect the code quality factor. 
	 * @param nbMax This is the maximal number of repetition that can affect the code quality factor, beyond that value, the code quality factor cannot be altered anymore.
	 */
	public SonarRule(String ruleCode, DoubleRange doubleRange, IntegerRange integerRange){
		this.ruleCode = ruleCode;
		this.doubleRange = doubleRange;
		this.integerRange = integerRange;
		this.modifier = "";
	}
	
	public SonarRule(String ruleCode, DoubleRange doubleRange, IntegerRange integerRange, String modifier) {
		this(ruleCode,doubleRange,integerRange);
		this.modifier = modifier;
	}
	
	public double getSubstractedValueFromOccurrence(int nbOccurrence) {
		double res = 0.0 ;

		if(nbOccurrence >= integerRange.getUpper()){
			if (!integerRange.areBoundsEqual()){
				res = doubleRange.getUpper();
			}else {
				res = doubleRange.getLower();
			}
		}
		else if(isInRange(nbOccurrence)) {
			int diferrenceBetweenIntegerRange = integerRange.getUpper() - integerRange.getLower();
			double diferrenceBetweenDoubleRange = doubleRange.getUpper() - doubleRange.getLower();
			double coeff = diferrenceBetweenDoubleRange/diferrenceBetweenIntegerRange;
			res = ((nbOccurrence - integerRange.getLower()) * coeff) + doubleRange.getLower();
		}
		return res;
	}
	
	public boolean isInRange(int nbOccurrence){
		return integerRange.getLower() <= nbOccurrence && nbOccurrence < integerRange.getUpper();
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public DoubleRange getDoubleRange() {
		return doubleRange;
	}

	public void setDoubleRange(DoubleRange doubleRange) {
		this.doubleRange = doubleRange;
	}

	public IntegerRange getIntegerRange() {
		return integerRange;
	}

	public void setIntegerRange(IntegerRange integerRange) {
		this.integerRange = integerRange;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	public String toString(){
		return "[ " + ruleCode + " , (" + doubleRange.getLower() + " , " + doubleRange.getUpper() + ") , (" + integerRange.getLower() + " , " + integerRange.getUpper() + ")]";
		
	}

}
