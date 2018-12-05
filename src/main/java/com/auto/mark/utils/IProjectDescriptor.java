package com.auto.mark.utils;

import java.io.File;

public interface IProjectDescriptor {
	/**
	 * @return The directory of the project.
	 */
	public File getDirectory();

	/**
	 * Set a new directory for this project's descriptor.
	 * 
	 * @param dir
	 *            The new directory
	 */
	public void setDirectory(File dir);

	/**
	 * @return The project's name.
	 */
	public String getProjectKey();

	
	/**
	 * @return the artifact id of the project.
	 */
	public String getArtifactId();
	
	/**
	 * @return the group id of the project.
	 */
	public String getGroupId();
	
	/**
	 * @return the project author.
	 */
	public String getAuthor();
	
	/**
	 * Set the project author.
	 */
	public void setAuthor(String author);
}
