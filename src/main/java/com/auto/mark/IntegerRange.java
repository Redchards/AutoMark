package com.auto.mark;

/**
 *	This class represent the interval between the minimal and the maximal occurrence of the rule.
 */
public class IntegerRange {
	
	private int upper;
	private int lower;
	
	/**
	 * Build a default range with 0 for each bound values.
	 */
	public IntegerRange() {
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
	public IntegerRange(int lower, int upper) throws InvalidRangeException {
		if (lower <= 0 || upper <= 0) {
			throw new InvalidRangeException(lower + "shouldn't be less than 0");
		}
		if(lower <= upper) {
			this.upper = upper;
			this.lower = lower;
		}
		else {
			throw new InvalidRangeException(lower + " <= " + upper + " is not respected.");
		}
	}

	/**
	 * @return the upper bound of the range.
	 */
	public int getUpper() {
		return upper;
	}

	/**
	 * @return the lower bound of the range.
	 */
	public void setUpper(int upper) {
		this.upper = upper;
	}

	/**
	 * Set the upper bound of the range.
	 * 
	 * @param upper The new upper bound.
	 */
	public int getLower() {
		return lower;
	}

	/**
	 * Set the lower bound of the range.
	 * 
	 * @param lower The new lower bound.
	 */
	public void setLower(int lower) {
		this.lower = lower;
	}
	
	/**
	 * Checks if the lower bound and the upper bound are equal.
	 * @return True if the lower bound equals the upper bound
	 */
	public boolean areBoundsEqual(){
		return getUpper()==getLower();
	}

}
