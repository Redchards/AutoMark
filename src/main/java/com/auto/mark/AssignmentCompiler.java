package com.auto.mark;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.Parent;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.MavenDescriptorException;
import com.auto.mark.utils.MavenProjectDescriptor;
import com.auto.mark.utils.PathBuilder;
import com.auto.mark.utils.SonarPropertyParser;
import com.auto.mark.utils.SonarPropertyParsingException;


/**
 * A class used to compile and launch the {@link TestEngine} of
 * each assignments in the target directory. Each assignment should be a
 * valid maven project, and the pom.xml should be immediately
 * accessible. It's used by the {@link CorrectionProcessManager} as part
 * of the correction and marking process.
 * 
 * TODO : To write the parent, may be a need at some point in the future
 * to use the more complex answer here :
 * http://stackoverflow.com/questions/4838591/is-there-a-library-for-reading-maven2-3-pom-xml-files
 * 
 * @see CorrectionProcessManager
 */

public class AssignmentCompiler {

	private Parent parent;
	private String pathToTests;
	private MavenInvocationEngine invocationEngine;
	
	private static final Logger mainLog = LogManager.getRootLogger();

		
	/**
	 * Constructs a compile using the tests presents in the directory given, and
	 * a managing parent pom.xml.
	 * 
	 * @param pathToTests
	 *            The path in which the compile will look for the tests.
	 * @param parent
	 *            The definition for the parent pom to use.
	 * @param config
	 * 			  The configuration of the current correction process.
	 * @throws AssignmentCompilationFailure
	 */
	public AssignmentCompiler(String pathToTests, Parent parent, IApplicationConfiguration config) throws AssignmentCompilationFailure {

		this.pathToTests = pathToTests;

		try {
			Properties sonarProperties = SonarPropertyParser.parse(config.getPropertiesFile());
			TestDependencyGenerator.generate(pathToTests, sonarProperties);
			
			File magicPomFile = new File(new PathBuilder(CommonDefaultConfigurationValues.getMagicPomPath()).append("pom.xml").toString());
			
			if(!magicPomFile.exists()) {
				MagicPomGenerator.generate();
			}
		} catch (IOException e) {
			throw new AssignmentCompilationFailure(CompilationPhase.PRECOMPILATION,
					"can't write the dependency pom.xml", e);
		} catch (SonarPropertyParsingException e) {
			throw new AssignmentCompilationFailure(CompilationPhase.PRECOMPILATION, "error when parsing the sonar property file", e);
		}

		this.parent = parent;
		
		try {
			invocationEngine = new MavenInvocationEngine();
		} catch (MavenInvocationEngineException e) {
			throw new AssignmentCompilationFailure(CompilationPhase.PRECOMPILATION, "failed to initialize the invocation engine", e);
		}
		invocationEngine.addAllGoals("clean", "compile");
	}

	/**
	 * Constructs a compile using the tests presents in the directory given, and
	 * a managing parent pom.xml.
	 * 
	 * @param testsDir
	 *            The directory in which the compile will look for the tests.
	 * @param parent
	 *            The definition for the parent pom to use.
	 * @param config
	 * 			  The configuration of the current correction process.
	 * @throws AssignmentCompilationFailure
	 * @throws IOException
	 */
	public AssignmentCompiler(File testsDir, Parent parent, IApplicationConfiguration config) throws AssignmentCompilationFailure {
		this(testsDir.toString(), parent, config);
	}

	/**
	 * Compile the project, if any, found at the path given in parameter.
	 * WARNING ! The compilation stops if the pom.xml is not present ! This
	 * should not happen ! Hence, we must check if there is a pom.xml present,
	 * or generate a dummy one.
	 * 
	 * @param projectDir
	 *            The path to the maven project
	 * @return MavenOutput the output of the maven command line.
	 * @throws AssignmentCompilationFailure
	 */
	public InvocationOutput compile(File projectDir) throws AssignmentCompilationFailure {

		InvocationOutput mavenOutput;

		MavenXpp3Reader reader = new MavenXpp3Reader();

		Model model;
		FileWriter fileWriter = null;
		
		MavenProjectDescriptor descriptor;
		
		try {
			descriptor = new MavenProjectDescriptor(projectDir);
		} catch (MavenDescriptorException e) {
			throw new AssignmentCompilationFailure(projectDir, "failed to build the project descriptor for the current maven project", e);
		}
		
		// NOTE : For some reason, using try with resources with fileWriter is
		// not working (quirk of MavenXpp3Writer).
		try (FileReader fileReader = new FileReader(descriptor.getPomPath())) {
			
			model = reader.read(fileReader);

			model.setParent(parent);
			model = removeJUnitDependency(model);
			MavenXpp3Writer writer = new MavenXpp3Writer();

			fileWriter = new FileWriter(descriptor.getPomPath());

			writer.write(fileWriter, model);

			mavenOutput = invocationEngine.execute(descriptor);
		} catch (IOException | XmlPullParserException e) {
			throw new AssignmentCompilationFailure(projectDir, "failed to set the parent pom.xml for compilation", e);
		} catch (MavenInvocationEngineException e) {
			throw new AssignmentCompilationFailure(projectDir, "failed to build to invocation engine", e);
		}  finally {
			if (fileWriter != null) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					mainLog.warn(e);
					fileWriter = null; // Most likely the file was already closed or never opened, so just disable the reference.
				}
			}
		}

		return mavenOutput;
	}

	/**
	 * Compile the project, if any, found in the directory given in parameter.
	 * WARNING ! The compilation stops if the pom.xml is not present ! This
	 * should not happen ! Hence, we must check if there is a pom.xml present,
	 * or generate a dummy one.
	 * 
	 * @param path
	 *            The directory's path of the Maven project
	 * @return MavenOutput the output of the maven command line.
	 * @throws AssignmentCompilationFailure
	 */
	public InvocationOutput compile(String path) throws AssignmentCompilationFailure {
		return compile(new File(path));
	}
	
	private Model removeJUnitDependency(Model model) {
		// We suppress the warning, we know what we're doing.
		// It should not be allowed to modify the model directly, hence why we first copy the dependency list, and then
		// set it back to the model.
		@SuppressWarnings("unchecked")
		List<Dependency> dependencies = new ArrayList<>(model.getDependencies());
		
		for(Iterator<Dependency> it = dependencies.iterator(); it.hasNext();){
			Dependency dependency = it.next();
			if("junit".equals(dependency.getArtifactId())){
				it.remove();
			}
		}
		
		model.setDependencies(dependencies);
		
		return model;
	}

	/**
	 * @return the path of the test directory.
	 */
	public String getPathToTests() {
		return pathToTests;
	}

}
