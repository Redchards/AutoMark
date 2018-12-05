package com.auto.mark.utils;

public class CharSequenceHelper {
	public static <T extends CharSequence> char getFirstOf(T sequence) {
		return sequence.charAt(0);
	}
	
	public static <T extends CharSequence> char getLastOf(T sequence) {
		return sequence.charAt(sequence.length() - 1);
	}
	
	public static <T extends CharSequence> String getFirstAsStrOf(T sequence) {
		return String.valueOf(sequence.charAt(0));
	}
	
	public static <T extends CharSequence> String getLastAsStrOf(T sequence) {
		return String.valueOf(sequence.charAt(sequence.length() - 1));
	}
	

}
