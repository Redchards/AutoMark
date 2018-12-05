package com.auto.mark;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.PluginManagement;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import com.auto.mark.utils.CommonDefaultConfigurationValues;

/**
 * An utility class used to generate dependency pom.xml.
 * It's used, among other things, by the {@link AssignmentCompiler} to set the tests directory.
 * @see AssignmentCompiler
 */
public class TestDependencyGenerator {
	
	private static final String TEST_GROUPID = CommonDefaultConfigurationValues.getDefaultDependencyPomGroupid();
	private static final String POM_MODEL_VERSION = CommonDefaultConfigurationValues.getDefaultPomModelVersion();
	private static final String TEST_PACKAGING = "pom";
	private static final String TEST_ARTIFACTID = CommonDefaultConfigurationValues.getDefaultDependencyPomArtifactid();
	private static final String DEPENDENCY_POM_PATH = "assignments";
	private static final String DEFAULT_PROJECT_LANGUAGE = "java";
	
	// Should automatize that or put it in a configuration class.
	// The version should be able to be updated !
	// Maybe use a property file ?
	private static final String BUILD_HELPER_PLUGIN_GROUPID = "org.codehaus.mojo";
	private static final String BUILD_HELPER_PLUGIN_ARTIFACTID = "build-helper-maven-plugin";
	private static final String BUILD_HELPER_PLUGIN_VERSION = CommonDefaultConfigurationValues.getDefaultBuildHelperPluginVersion();
	
	private static final Logger mainLog = LogManager.getRootLogger();
	
	/**
	 * Utility method designed to generate the dependency pom.xml.
	 * 
	 * @param testPath The path of the directory containing the unit testing files.
	 * @param properties The properties to be used in the resulting pom.xml.
	 * 
	 * @throws IOException
	 */
	public static void generate(String testPath, Properties properties) throws IOException {
		File pomFile = new File(DEPENDENCY_POM_PATH, "pom.xml");
		
		if(pomFile.exists()) {
			if(!pomFile.delete()) {
				mainLog.debug("The dependency pom does not exists. Skipping deletion.");
			}
		}
		
		if(!pomFile.createNewFile()) {
			mainLog.debug("The dependency pom already exists (most likely failed to be deleted). Skipping creation.");
		}
		// pomFile.deleteOnExit(); // This line can be commented out for testing purposes.
			
		Model model = new Model();
			
		model.setModelVersion(POM_MODEL_VERSION);
		model.setGroupId(TEST_GROUPID);
		model.setArtifactId(TEST_ARTIFACTID); // pomFile.getParentFile().getName()
		model.setVersion("1.0");
		model.setPackaging(TEST_PACKAGING);
		// model.setName(TEST_GENERIC_NAME);
		
		
		Properties pomProperties = new Properties();
		pomProperties.put("sonar.language", DEFAULT_PROJECT_LANGUAGE);
		pomProperties.put("sonar.host.url", properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyUrlLabel()));
		if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyLoginLabel())) {
			pomProperties.put("sonar.login", properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyLoginLabel()));
			
			if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel())) {
				pomProperties.put("sonar.password", properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel()));
			}
		}
		
		if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel())) {
			pomProperties.put("sonar.organization", properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel()));
		}
		
		model.setProperties(pomProperties);
		
		List<Plugin> pluginList = new ArrayList<>();
		Build build = new Build();

		Plugin buildHelperPlugin = new Plugin();
		
		buildHelperPlugin.setGroupId(BUILD_HELPER_PLUGIN_GROUPID);
		buildHelperPlugin.setArtifactId(BUILD_HELPER_PLUGIN_ARTIFACTID);
		buildHelperPlugin.setVersion(BUILD_HELPER_PLUGIN_VERSION);
		
		PluginExecution exec = new PluginExecution();
		/*exec.addGoal("add-test-source");
		exec.setPhase("generate-test-sources");
		exec.setId("add-test-source");*/
		
		exec.addGoal("add-source");
		exec.setPhase("generate-sources");
		exec.setId("add-source");
		
		
		Xpp3Dom buildHelperPluginConfiguration = new Xpp3Dom("configuration");
		Xpp3Dom sourceListTag = new Xpp3Dom("sources");
		Xpp3Dom sourceTag = new Xpp3Dom("source");
		
		sourceTag.setValue(new File(testPath).getAbsolutePath());
		sourceListTag.addChild(sourceTag);
		buildHelperPluginConfiguration.addChild(sourceListTag);
		
		exec.setConfiguration(buildHelperPluginConfiguration);
		
		buildHelperPlugin.setExecutions(Arrays.asList(exec));

		pluginList.add(buildHelperPlugin);
		
		PluginManagement manager = new PluginManagement();
		manager.setPlugins(pluginList);
		
		build.setPluginManagement(manager);
		model.setBuild(build);
			
		MavenXpp3Writer writer = new MavenXpp3Writer();
		writer.write(new FileWriter(pomFile), model);
	}
}
