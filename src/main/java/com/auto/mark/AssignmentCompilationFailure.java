package com.auto.mark;

import java.io.File;

import com.auto.mark.utils.GenericMessageException;
import com.auto.mark.utils.IProjectDescriptor;

/** An exception type thrown by the {@link AssignmentCompiler} */
public class AssignmentCompilationFailure extends GenericMessageException {
	/**
	 * Generated UID.
	 */
	private static final long serialVersionUID = -467577842266682596L;

	public AssignmentCompilationFailure(CompilationPhase phase, String msg) {
		super("Error during the " + phase.toString() + " phase : " + msg);
	}
	
	public AssignmentCompilationFailure(String projectPath, String msg) {
		super("Failed to compile the project '" + projectPath +"' : " + msg);
	}
	
	public AssignmentCompilationFailure(File projectDir, String msg) {
		this(projectDir.toString(), msg);
	}
	
	public AssignmentCompilationFailure(IProjectDescriptor projectDescriptor, String msg) {
		this(projectDescriptor.getDirectory(), msg);
	}
	
	public AssignmentCompilationFailure(CompilationPhase phase, String msg, Throwable cause) {
		super("Error during the " + phase.toString() + " phase : " + msg, cause);
	}
	
	public AssignmentCompilationFailure(String projectPath, String msg, Throwable cause) {
		super("Failed to compile the project '" + projectPath +"' : " + msg, cause);
	}
	
	public AssignmentCompilationFailure(File projectDir, String msg, Throwable cause) {
		this(projectDir.toString(), msg, cause);
	}
	
	public AssignmentCompilationFailure(IProjectDescriptor projectDescriptor, String msg, Throwable cause) {
		this(projectDescriptor.getDirectory(), msg, cause);
	}
}
