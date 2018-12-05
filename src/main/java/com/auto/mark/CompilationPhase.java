package com.auto.mark;

/**
 * A simple enumeration used to describe the different compilation phases
 */
enum CompilationPhase {
	
	/*
	 * Add more phases 
	 */
	PRECOMPILATION("precompilation"),
	TEST("test"),
	ANALYSIS("analysis"),
	POSTCOMPILATION("postcompilation");

	private final String name;
	
	private CompilationPhase(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
