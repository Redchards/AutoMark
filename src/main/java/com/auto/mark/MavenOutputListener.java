package com.auto.mark;

import org.apache.maven.shared.invoker.InvocationRequest;

import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.MavenProjectDescriptor;

/**
 * A class used to catch errors from a maven invoker. It sets up a
 * listener that catches all the errors returned by the Maven invocation's
 * instance for a given Maven project.
 *
 * @see MavenInvocationEngine
 */
class MavenOutputListener {

	private static final String ERROR_LOG_LABEL = "[ERROR]";
	private static final String WARNING_LOG_LABEL = "[WARNING]";
	private static final String INFO_LOG_LABEL = "[INFO]";
	private static final String DEBUG_LOG_LABEL = "[DEBUG]";
	
	/**
	 * The Maven project target by the MavenInvocationEngine.
	 */
	private MavenProjectDescriptor projectDescriptor;
	
	
	private InvocationOutput mavenOutput;

	/**
	 * The MavenInvocationEngine's InvocationRequest instance.
	 */
	private InvocationRequest request;
		
	public MavenOutputListener() {
		projectDescriptor = null;
		request = null;
	}

	public MavenOutputListener(MavenProjectDescriptor projectDescriptor, InvocationRequest request) {
		this.projectDescriptor = projectDescriptor;
		this.request = request;
		listenTo(this.projectDescriptor, this.request);
	}

	/**
	 * Sets a new MavenErrorListener for a given project for the same
	 * MavenInvocationEngine. s
	 * 
	 * @param projectDescriptor
	 *            The given project to analyze.
	 * @param request
	 *            The InvocationRequest used for Maven compilation phase.
	 */
	public void listenTo(MavenProjectDescriptor projectDescriptor, InvocationRequest request) {
		this.request = request;
		this.projectDescriptor = projectDescriptor;
		this.mavenOutput = new MavenInvocationOutput(projectDescriptor);
		
		request.setOutputHandler((line) -> {
			if(line.startsWith(ERROR_LOG_LABEL)) {
				mavenOutput.addError(line.substring(ERROR_LOG_LABEL.length(), line.length()));
			}
			else if(line.startsWith(WARNING_LOG_LABEL)) {
				mavenOutput.addWarning(line.substring(WARNING_LOG_LABEL.length(), line.length()));
			}
			else if(line.startsWith(INFO_LOG_LABEL)) {
				mavenOutput.addInfo(line.substring(INFO_LOG_LABEL.length(), line.length()));
			}
			else if(line.startsWith(CommonDefaultConfigurationValues.getAutoMarkDataOutputLabel())) {
				mavenOutput.setAutoMarkData(line.substring(CommonDefaultConfigurationValues.getAutoMarkDataOutputLabel().length(), line.length()));
			}
			else { // Log everything at this point
				if(line.startsWith(DEBUG_LOG_LABEL)) {
					mavenOutput.addDebug(line.substring(DEBUG_LOG_LABEL.length(), line.length()));
				}
				else {
					mavenOutput.addDebug(line);
				}
			}
		});
	}

	/**
	 * @return This instance's Maven's InvocationRequest instance.
	 */
	public InvocationRequest getRequest() {
		return request;
	}
	
	public InvocationOutput getMavenOutput() {
		return mavenOutput;
	}
}
