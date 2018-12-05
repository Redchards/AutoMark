package com.auto.mark.utils;

/**
 * A simple enumeration describing the verbose level the console should have.
 */
public enum VerboseLevel {
	MINIMAL, // Enables only progress report and error report.
	LOW,	 // Enables progress and error report as well as warning report.
	HIGH,    // Enables everything from previous level as well as info report.
	ALL		 // Enables every report possible (debug, info, warning, error).
}
