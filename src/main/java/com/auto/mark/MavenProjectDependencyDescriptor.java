package com.auto.mark;

/**
 * A simple class used to represent a Maven dependency used, amongst others, in the {@link MagicPomGenerator}
 * utility class to ease the dependency writing.
 */
public class MavenProjectDependencyDescriptor {
	private static final String DEFAULT_SCOPE = "compile";
	
	private String groupId;
	private String artifactId;
	private String version;
	private String scope;
	private boolean useDefaultScope;
	
	public MavenProjectDependencyDescriptor(String groupId, String artifactId, String version) {
		this(groupId, artifactId, version, DEFAULT_SCOPE);
	
		useDefaultScope = true;
	}
	
	public MavenProjectDependencyDescriptor(String groupId, String artifactId, String version, String scope) {
		setGroupId(groupId);
		setArtifactId(artifactId);
		setVersion(version);
		setScope(scope);
		
		useDefaultScope = false;
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	public boolean useDefaultScope() {
		return useDefaultScope;
	}
}
