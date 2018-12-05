package com.auto.mark.utils;

import org.apache.maven.model.Parent;

public class MavenParentGenerator {
	
	private static final String DEFAULT_GROUPID = "";
	private static final String DEFAULT_VERSION = "1.0";
	
	public static Parent generate(String artifactId, String groupId, String version) {
    	Parent parent = new Parent();
    	parent.setArtifactId(artifactId);
    	parent.setGroupId(groupId);
    	parent.setVersion(version);
    	
    	return parent;
	}
	
	public static Parent generate(String artifactId, String groupId) {
		return generate(artifactId, groupId, DEFAULT_VERSION);
	}
	
	public static Parent generate(String artifactId) {
		return generate(artifactId, DEFAULT_GROUPID);
	}
}
