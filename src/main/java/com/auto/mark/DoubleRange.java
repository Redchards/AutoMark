package com.auto.mark;

/**
 *	This class represent the interval between the minimal and the maximal value which will be subtracted to the code quality factor.
 */
public class DoubleRange {
	
	private double upper;
	private double lower;
	
	/**
	 * Build a default range with 0 for each bound values.
	 */
	public DoubleRange() {
		this.upper = 0;
		this.lower = 0;
	}
	
	/**
	 * Build a range using the lower and upper bound passed in parameter.
	 * 
	 * @param lower The lower bound of the range.
	 * @param upper The upper bound of the range.
	 * 
	 * @throws InvalidRangeException
	 */
	public DoubleRange(Double lower, Double upper) throws InvalidRangeException {
		if (lower < 0 || upper < 0) {
			throw new InvalidRangeException(lower + "shouldn't be less than 0");
		}
		if(lower <= upper) {
			this.upper = upper;
			this.lower = lower;
		}
		else {
			throw new InvalidRangeException(lower + " <= " + upper + " is not respected");
		}
	}

	/**
	 * @return the upper bound of the range.
	 */
	public double getUpper() {
		return upper;
	}

	/**
	 * @return the lower bound of the range.
	 */
	public double getLower() {
		return lower;
	}

	/**
	 * Set the upper bound of the range.
	 * 
	 * @param upper The new upper bound.
	 */
	public void setUpper(double upper) {
		this.upper = upper;
	}

	/**
	 * Set the lower bound of the range.
	 * 
	 * @param lower The new lower bound.
	 */
	public void setLower(double lower) {
		this.lower = lower;
	}
	
}
