package com.auto.mark.utils;

import java.io.File;

/**
 * A class regrouping shared specific values used in the program.
 */
public class CommonDefaultConfigurationValues {
	private CommonDefaultConfigurationValues() {}
	
	private static final String DEFAULT_SONAR_PROPERTIES_FILE_NAME = "sonar.properties";
	private static final String DEFAULT_MARKING_PARAMETERS_FILE_NAME = "marking_parameters.csv";
	private static final String DEFAULT_RESULTS_OUTPUT_FILE_NAME = "results.csv";
	private static final String DEFAULT_DETAILS_OUTPUT_FILE_NAME = "details.csv";
	private static final String DEFAULT_OUTPUT_DIRECTORY_PATH = "assignments" + File.separator + "results";
	private static final VerboseLevel DEFAULT_VERBOSE_LEVEL = VerboseLevel.MINIMAL;
	private static final String DEFAULT_POM_MODEL_VERSION = "4.0.0";
	private static final String DEFAULT_DEPENDENCY_POM_GROUPID = "marked.test";
	private static final String DEFAULT_DEPENDENCY_POM_ARTIFACTID = "GeneratedDependencies";
	private static final String DEFAULT_DEPENDENCY_POM_VERSION = "1.0";
	private static final String DEFAULT_MAGIC_POM_GROUPID = DEFAULT_DEPENDENCY_POM_GROUPID;
	private static final String DEFAULT_MAGIC_POM_ARTIFACTID = "MainPomHook";
	private static final String DEFAULT_MAGIC_POM_VERSION = "1.0";
	private static final String DEFAULT_BUILD_HELPER_PLUGIN_VERSION = "1.12";
	private static final String MAGIC_POM_PATH = new PathBuilder("assignments").append("files").toString();
	private static final String DEPENDENCY_POM_PATH = "assignments";
	private static final String SONAR_PROPERTY_URL_LABEL = "url";
	private static final String SONAR_PROPERTY_LOGIN_LABEL = "login";	
	// Sonar is detecting this as a hard coded password. This is, however, simply a label used in our property file.
	// So we can safely disable the sonar analysis for this line.
	private static final String SONAR_PROPERTY_PASSWD_LABEL = "password"; //NOSONAR
	private static final String SONAR_PROPERTY_ORGANIZATION_LABEL = "organization";
	private static final String AUTO_MARK_DATA_OUTPUT_LABEL = "[AUTO-MARK-DATA]";


	public static String getDefaultBuildHelperPluginVersion() {
		return DEFAULT_BUILD_HELPER_PLUGIN_VERSION;
	}

	public static String getDefaultSonarPropertiesFileName() {
		return DEFAULT_SONAR_PROPERTIES_FILE_NAME;
	}
	
	public static String getDefaultMarkingParametersFileName() {
		return DEFAULT_MARKING_PARAMETERS_FILE_NAME;
	}
	
	public static String getResultsFileName() {
		return DEFAULT_RESULTS_OUTPUT_FILE_NAME;
	}
	
	public static String getDetailsFileName() {
		return DEFAULT_DETAILS_OUTPUT_FILE_NAME;
	}
	
	public static String getDefaultOutputDirectoryPath() {
		return DEFAULT_OUTPUT_DIRECTORY_PATH;
	}
	
	public static VerboseLevel getDefaultVerboseLevel() {
		return DEFAULT_VERBOSE_LEVEL;
	}
	
	public static String getDefaultPomModelVersion() {
		return DEFAULT_POM_MODEL_VERSION;
	}

	public static String getDefaultResultsOutputFileName() {
		return DEFAULT_RESULTS_OUTPUT_FILE_NAME;
	}

	public static String getDefaultDetailsOutputFileName() {
		return DEFAULT_DETAILS_OUTPUT_FILE_NAME;
	}

	public static String getDefaultDependencyPomGroupid() {
		return DEFAULT_DEPENDENCY_POM_GROUPID;
	}

	public static String getDefaultDependencyPomArtifactid() {
		return DEFAULT_DEPENDENCY_POM_ARTIFACTID;
	}

	public static String getDefaultDependencyPomVersion() {
		return DEFAULT_DEPENDENCY_POM_VERSION;
	}

	public static String getDefaultMagicPomGroupid() {
		return DEFAULT_MAGIC_POM_GROUPID;
	}

	public static String getDefaultMagicPomArtifactid() {
		return DEFAULT_MAGIC_POM_ARTIFACTID;
	}

	public static String getDefaultMagicPomVersion() {
		return DEFAULT_MAGIC_POM_VERSION;
	}
	
	public static String getMagicPomPath() {
		return MAGIC_POM_PATH;
	}

	public static String getDependencyPomPath() {
		return DEPENDENCY_POM_PATH;
	}

	public static String getSonarPropertyUrlLabel() {
		return SONAR_PROPERTY_URL_LABEL;
	}

	public static String getSonarPropertyLoginLabel() {
		return SONAR_PROPERTY_LOGIN_LABEL;
	}

	public static String getSonarPropertyPasswordLabel() {
		return SONAR_PROPERTY_PASSWD_LABEL;
	}

	public static String getAutoMarkDataOutputLabel() {
		return AUTO_MARK_DATA_OUTPUT_LABEL;
	}

	public static String getSonarPropertyOrganizationLabel() {
		return SONAR_PROPERTY_ORGANIZATION_LABEL;
	}
}
