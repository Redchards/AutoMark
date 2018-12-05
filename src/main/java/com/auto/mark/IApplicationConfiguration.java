package com.auto.mark;

import java.io.File;

import com.auto.mark.utils.VerboseLevel;

/**
 * The common interface used to define the configuration application.
 * @see cmd.auto.mark.ApplicationConfiguration
 * @see gui.auto.mark.MainController
 */
public interface IApplicationConfiguration {
	/**
	 * @return the directory where the projects are located.
	 */
	public File getProjectsDir();
	
	/**
	 * @return the directory where the tests are located.
	 */
	public File getTestsDir();

	/**
	 * @return the file containing the SonarQube properties.
	 */
	public File getPropertiesFile();

	/**
	 * @return the file containing the marking parameters.
	 */
	public File getMarkingParametersFile();
	
	/**
	 * @return the output directory.
	 */
	public File getOutputDirectory();
	
	/**
	 * @return the log verbose level.
	 */
	public VerboseLevel getVerboseLevel();
	
	/**
	 * @return true if the application is to be started in GUI form, false otherwise.
	 */
	public boolean isGui();
}
