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
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.Plugin;
import org.apache.maven.model.PluginExecution;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.Xpp3Dom;

import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.MavenParentGenerator;
import com.auto.mark.utils.PathBuilder;

/**
 * A simple utility class used to generate the "Magic Pom" (ref : the project's paper documentation).
 */
public class MagicPomGenerator {
	
	/* Just constants used for definition and enable later modifications. */
	private static final String TEST_PACKAGING = "pom";
	private static final String DEFAULT_PROJECT_LANGUAGE = "java";
	private static final String DEFAULT_FILE_ENCODING = "UTF-8";
	private static final String DEFAULT_RUNNER_CLASS = com.auto.mark.runner.MarkedTestRunner.class.toString();
	
	private static final String BUILD_HELPER_PLUGIN_GROUPID = "org.codehaus.mojo";
	private static final String BUILD_HELPER_PLUGIN_ARTIFACTID = "build-helper-maven-plugin";
	private static final String BUILD_HELPER_PLUGIN_VERSION = CommonDefaultConfigurationValues.getDefaultBuildHelperPluginVersion();
	
	private static final String SONAR_PLUGIN_GROUPID = "org.sonarsource.scanner.maven";
	private static final String SONAR_PLUGIN_ARTIFACTID = "sonar-maven-plugin";
	private static final String SONAR_PLUGIN_VERSION = "3.2";
	
	private static final String EXEC_PLUGIN_GROUPID = "org.codehaus.mojo";
	private static final String EXEC_PLUGIN_ARTIFACTID = "exec-maven-plugin";
	private static final String EXEC_PLUGIN_VERSION = "1.5.0";
	
	private static final String DEFAULT_SRC_FILE_DIR
		= new PathBuilder("..").append("..").append("..")
		.append("src").append("main").append("java")
		.append("com").append("auto").append("mark").toString();		
	
	private static final String DEFAULT_ANNOTATION_SRC_PATH
		= new PathBuilder(DEFAULT_SRC_FILE_DIR).append("annotation").toString();
	
	private static final String DEFAULT_RUNNER_SRC_PATH
		= new PathBuilder(DEFAULT_SRC_FILE_DIR).append("runner").toString();
	
	private static final String DEFAULT_UTILS_SRC_PATH
		= new PathBuilder(DEFAULT_SRC_FILE_DIR).append("utils").toString();
	
	private static final String[] DEFAULT_SRC_PATH_LIST = new String[] {
			DEFAULT_ANNOTATION_SRC_PATH,
			DEFAULT_RUNNER_SRC_PATH,
			DEFAULT_UTILS_SRC_PATH
	};
	
	private static final List<MavenProjectDependencyDescriptor> dependencyList = Arrays.asList(new MavenProjectDependencyDescriptor[] {
			new MavenProjectDependencyDescriptor("junit", "junit", "4.12", "compile"),
			new MavenProjectDependencyDescriptor("org.reflections", "reflections-maven", "0.9.9-RC2"),
			new MavenProjectDependencyDescriptor("net.lingala.zip4j", "zip4j", "1.3.2"),
			new MavenProjectDependencyDescriptor("org.apache.commons", "commons-csv", "1.4"), // TODO : remove this
			new MavenProjectDependencyDescriptor("org.glassfish", "javax.json", "1.0.4"),
			new MavenProjectDependencyDescriptor("commons-codec", "commons-codec", "1.10")
	});
	
	private static final Logger mainLog = LogManager.getRootLogger();
	
	/**
	 * Utility method designed to generate the dependency pom.xml.
	 * 
	 * @param testPath The path of the directory containing the unit testing files.
	 * @param properties The properties to be used in the resulting pom.xml.
	 * 
	 * @throws IOException
	 */
	public static void generate() throws IOException {
		File pomFile = new File(CommonDefaultConfigurationValues.getMagicPomPath(), "pom.xml");
		
		if(pomFile.exists()) {
			if(!pomFile.delete()) {
				mainLog.debug("The magic pom does not exists. Skipping deletion.");
			}
		}
		
		if(!pomFile.createNewFile()) {
			mainLog.debug("The magic pom already exists (most likely failed to be deleted). Skipping creation.");
		}
		// pomFile.deleteOnExit(); // This line can be commented out for testing purposes.
			
		Model model = new Model();
			
		model.setModelVersion(CommonDefaultConfigurationValues.getDefaultPomModelVersion());
		model.setGroupId(CommonDefaultConfigurationValues.getDefaultMagicPomGroupid());
		model.setArtifactId(CommonDefaultConfigurationValues.getDefaultMagicPomArtifactid());
		model.setVersion(CommonDefaultConfigurationValues.getDefaultMagicPomVersion());
		model.setPackaging(TEST_PACKAGING);
		// model.setName(TEST_GENERIC_NAME);
		
		
		Parent parent = MavenParentGenerator.generate(CommonDefaultConfigurationValues.getDefaultDependencyPomArtifactid(),
													  CommonDefaultConfigurationValues.getDefaultDependencyPomGroupid(),
													  CommonDefaultConfigurationValues.getDefaultDependencyPomVersion());
		
		model.setParent(parent);
		
		
		Properties pomProperties = new Properties();
		pomProperties.put("sonar.language", DEFAULT_PROJECT_LANGUAGE);
		pomProperties.put("project.build.sourceEncoding", DEFAULT_FILE_ENCODING);
		
		model.setProperties(pomProperties);
		
		List<Plugin> pluginList = new ArrayList<>();
		Build build = new Build();

		
		pluginList.add(generateBuildHelperPlugin());
		pluginList.add(generateSonarPlugin());
		pluginList.add(generateExecPlugin());
		
		build.setPlugins(pluginList);
		model.setBuild(build);
		
			
		for(MavenProjectDependencyDescriptor descriptor : dependencyList) {
			Dependency dep = new Dependency();
			
			dep.setGroupId(descriptor.getGroupId());
			dep.setArtifactId(descriptor.getArtifactId());
			dep.setVersion(descriptor.getVersion());
			
			if(!descriptor.useDefaultScope()) {
				dep.setScope(descriptor.getScope());
			}
			
			model.addDependency(dep);
		}
		
		MavenXpp3Writer writer = new MavenXpp3Writer();
		writer.write(new FileWriter(pomFile), model);
	}
	
	/*
	 * Generate the build helper plugin definition according to the static class parameters.
	 * @return the plugin generated.
	 */
	private static Plugin generateBuildHelperPlugin() {
		Plugin buildHelperPlugin = new Plugin();
		
		buildHelperPlugin.setGroupId(BUILD_HELPER_PLUGIN_GROUPID);
		buildHelperPlugin.setArtifactId(BUILD_HELPER_PLUGIN_ARTIFACTID);
		buildHelperPlugin.setVersion(BUILD_HELPER_PLUGIN_VERSION);
		
		PluginExecution buildHelperExec = new PluginExecution();
		/*exec.addGoal("add-test-source");
		exec.setPhase("generate-test-sources");
		exec.setId("add-test-source");*/
		
		buildHelperExec.addGoal("add-source");
		buildHelperExec.setPhase("generate-sources");
		buildHelperExec.setId("add-source");
		
		
		Xpp3Dom buildHelperPluginConfiguration = new Xpp3Dom("configuration");
		Xpp3Dom sourceListTag = new Xpp3Dom("sources");
		sourceListTag.setAttribute("combine.children", "append");
		
		for(String path : DEFAULT_SRC_PATH_LIST) {
			Xpp3Dom sourceTag = new Xpp3Dom("source");
			
			sourceTag.setValue(path);
			sourceListTag.addChild(sourceTag);
		}
		
		buildHelperPluginConfiguration.addChild(sourceListTag);
		buildHelperExec.setConfiguration(buildHelperPluginConfiguration);
		
		buildHelperPlugin.setExecutions(Arrays.asList(buildHelperExec));

		return buildHelperPlugin;
	}
	
	/*
	 * Generate the sonar plugin definition according to the static class parameters.
	 * @return the plugin generated.
	 */
	private static Plugin generateSonarPlugin() {
		Plugin sonarPlugin = new Plugin();
		
		sonarPlugin.setGroupId(SONAR_PLUGIN_GROUPID);
		sonarPlugin.setArtifactId(SONAR_PLUGIN_ARTIFACTID);
		sonarPlugin.setVersion(SONAR_PLUGIN_VERSION);
		
		PluginExecution sonarExec = new PluginExecution();
		
		sonarExec.addGoal("sonar");
		
		sonarPlugin.setExecutions(Arrays.asList(sonarExec));
		
		return sonarPlugin;
	}
	
	/*
	 * Generate the exec plugin definition according to the static class parameters.
	 * @return the plugin generated.
	 */
	private static Plugin generateExecPlugin() {
		Plugin execPlugin = new Plugin();
		
		execPlugin.setGroupId(EXEC_PLUGIN_GROUPID);
		execPlugin.setArtifactId(EXEC_PLUGIN_ARTIFACTID);
		execPlugin.setVersion(EXEC_PLUGIN_VERSION);
		
		Xpp3Dom execPluginConfiguration = new Xpp3Dom("configuration");
		
		Xpp3Dom mainClassTag = new Xpp3Dom("mainClass");
		mainClassTag.setValue(DEFAULT_RUNNER_CLASS.substring(6, DEFAULT_RUNNER_CLASS.length()));
		execPluginConfiguration.addChild(mainClassTag);
		
		execPlugin.setConfiguration(execPluginConfiguration);
		
		return execPlugin;
	}
}
