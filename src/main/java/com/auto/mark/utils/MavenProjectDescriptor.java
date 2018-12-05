package com.auto.mark.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * This class is used to gather informations about a specific Maven project,
 * such as its directory's path and its name.
 */

public class MavenProjectDescriptor implements IProjectDescriptor {

	private static final String MAVEN_POM_NAME = "pom.xml";

	/**
	 * The Maven project's directory's path.
	 */
	private File dir;

	private String artifactId;

	private String groupId;

	private String author;

	/**
	 * Constructs a simple "Maven project descriptor" described by its path, its author,
	 * its group id and its artifact id.
	 * 
	 * @param dir The project's directory
	 * @throws MavenDescriptorException
	 */
	public MavenProjectDescriptor(File dir) throws MavenDescriptorException {
		this.dir = dir;

		MavenXpp3Reader reader = new MavenXpp3Reader();

		Model model;

		// NOTE : For some reason, using try with resources with fileWriter is
		// not working (quirk of MavenXpp3Writer).
		try (FileReader fileReader = new FileReader(getPomPath())) {
			model = reader.read(fileReader);
			artifactId = model.getArtifactId();
			groupId = model.getGroupId();
			// Gets the "author" argument in the pom.xml properties scope. If not set, default to the directory name.
			if(model.getProperties().containsKey("author")) {
				author = model.getProperties().getProperty("author");
			}
			else {
				author = dir.getName();
			}
		} catch (IOException | XmlPullParserException e) {
			throw new MavenDescriptorException(getDirectory().getPath(), "failed to read to pom.xml", e);
		}
	}

	/*
	 * Constructs a simple "Maven project descriptor" described by its path, its name,
	 * its group id and its artifact id.
	 * @param dir The project's directory
	 * @throws MavenDescriptorException
	 * public MavenProjectDescriptor(File dir) throws MavenDescriptorException {
	 * this(dir, dir.getName());
	 * }
	 */
	/**
	 * Constructs a simple "Maven project descriptor" described by its path and
	 * name.
	 * 
	 * @param path The project's directory path
	 * @throws MavenDescriptorException
	 */
	public MavenProjectDescriptor(String path) throws MavenDescriptorException {
		this(new File(path));
	}

	/**
	 * @return The directory of the project.
	 */
	public File getDirectory() {
		return dir;
	}

	/**
	 * Sets a new directory for this project's descriptor.
	 * 
	 * @param dir
	 *            The new directory
	 */

	public void setDirectory(File dir) {
		this.dir = dir;
	}

	/**
	 * @return The project's name.
	 */
	@Override
	public String getProjectKey() {
		return groupId + ":" + artifactId;
	}

	/**
	 * @return The artifact id of the project.
	 */
	@Override
	public String getArtifactId() {
		return artifactId;
	}

	/**
	 * @return The group id of the project.
	 */
	@Override
	public String getGroupId() {
		return groupId;
	}

	/**
	 * @return The project's author.
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/**
	 * Set the project's author.
	 */
	@Override
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 
	 * @return The path to this Maven project's pom.xml file.
	 */
	public String getPomPath() {
		PathBuilder pathBuilder = new PathBuilder(this.getDirectory().getAbsolutePath());
		pathBuilder.append(MAVEN_POM_NAME);

		return pathBuilder.toString();
	}
}
