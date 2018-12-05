package com.auto.mark.runner;

import java.io.File;

import com.auto.mark.utils.IProjectDescriptor;

public class MockProjectDetails implements IProjectDescriptor {

	private String artifactId;

	private String groupId;

	public MockProjectDetails(String artifactId, String groupId) {
		this.artifactId = artifactId;
		this.groupId = groupId;
	}

	@Override
	public String getArtifactId() {
		return artifactId;
	}

	@Override
	public File getDirectory() {
		return new File(".");
	}

	@Override
	public void setDirectory(File dir) {
	}

	@Override
	public String getProjectKey() {
		return groupId + ":" + artifactId;
	}

	@Override
	public String getGroupId() {
		return groupId;
	}

	@Override
	public String getAuthor() {
		return "test";
	}

	@Override
	public void setAuthor(String author) {}

}
