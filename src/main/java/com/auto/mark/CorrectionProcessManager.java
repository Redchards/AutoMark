package com.auto.mark;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Parent;

import com.auto.mark.runner.MarkedTestRunner;
import com.auto.mark.runner.TestResult;
import com.auto.mark.utils.IProjectDescriptor;
import com.auto.mark.utils.MavenDescriptorException;
import com.auto.mark.utils.MavenParentGenerator;
import com.auto.mark.utils.MavenProjectDescriptor;
import com.auto.mark.utils.SonarPropertyParsingException;
import com.auto.mark.utils.TestResultSerializationException;
import com.auto.mark.utils.TestResultSerializer;

/**
 * The main managing class regarding the correction of the projects.
 * Given an {@link IApplicationConfiguration} and an {@link ProgressHandlerInterface},
 * this class will both launch and compile every projects contained in the porjects folder
 * pointed out by the {@link IApplicationConfiguration} and report the current process progress
 * and errors encountered if any.
 */
public class CorrectionProcessManager {

	private static final File PROJECTS_TARGET_DIR = new File("assignments" + File.separator + "files");

	private static final String PARENT_POM_ARTIFACTID = "MainPomHook";

	private static final String PARENT_POM_GROUPID = "marked.test";

	private static final String PARENT_POM_VERSION = "1.0";

	private static final Logger jobLog = LogManager.getLogger("infoLog");

	private static final Logger errorLog = LogManager.getLogger("errorLog");

	private static final Class<MarkedTestRunner> RUNNER_CLASS = com.auto.mark.runner.MarkedTestRunner.class;

	private IApplicationConfiguration config;

	private ProgressHandlerInterface progressHandler;

	public CorrectionProcessManager(IApplicationConfiguration config, ProgressHandlerInterface progressHandler) {
		this.config = config;
		this.progressHandler = progressHandler;
	}

	/**
	 * Launch the correction process and put the results in the designated
	 * folder.
	 * 
	 * @throws AssignmentCompilationFailure
	 */
	public void execute() throws AssignmentCompilationFailure {
		progressHandler.reset();

		try {
			LocalDirectoryFetcher fetcher = new LocalDirectoryFetcher();
			fetcher.fetch(config.getProjectsDir(), PROJECTS_TARGET_DIR);
			Parent parent = MavenParentGenerator.generate(PARENT_POM_ARTIFACTID, PARENT_POM_GROUPID, PARENT_POM_VERSION);
			AssignmentCompiler compiler = new AssignmentCompiler(config.getTestsDir(), parent, config);
	
			FileFilter filter = new FileFilter() {
				@Override
				public boolean accept(File file) {
					return file.isDirectory();
				}
			};
			
			progressHandler.setSampleCount(config.getProjectsDir().listFiles(filter).length);
			processProjectsInDirectory(compiler, PROJECTS_TARGET_DIR);
			progressHandler.update();

		}
		catch (InvalidPathException e) {
			throw new AssignmentCompilationFailure(CompilationPhase.PRECOMPILATION, "path not found", e);
		} catch (FetchingException e) {
			throw new AssignmentCompilationFailure(CompilationPhase.PRECOMPILATION, "can't fetch the directory", e);
		} finally {
			progressHandler.end();
		}
	}

	/**
	 * Set the progressHandler.
	 * 
	 * @param progressHandler The {@ProgressHandlerInterface} that will be used to track the progress.
	 */
	public void setProgressHandler(ProgressHandlerInterface progressHandler) {
		this.progressHandler = progressHandler;
	}

	/**
	 * @return the current progress handler used by this class.
	 */
	public ProgressHandlerInterface getProgressHandler() {
		return progressHandler;
	}

	/*
	 * Utility method used to log the results given in the format of a list of
	 * {@link Strings}.
	 */
	private void logResult(InvocationOutput mavenOutput) {

		List<String> result = mavenOutput.getErrorReport().getMessageList();

		if (result.isEmpty()) {
			jobLog.info("Success !                           ");
		} else {
			errorLog.error("Failed with :                   ");
			for (String err : result) {
				errorLog.error(err);
			}
		}
		

		jobLog.info("");
	}

	/*
	 * Utility method used to execute the {@link AssigmnetCompiler} on a list of
	 * projects, execute them, log eventual errors and write a report for the projects
	 * for which the compilation filed.
	 * 
	 * @param compiler The {@link AssignmentCompiler} to be used to compile projects.
	 * @param projectsTargetDir The directory containing the projects to be processed.
	 * 
	 * @throws AssignmentCompilationFailure
	 */
	private void processProjectsInDirectory(AssignmentCompiler compiler, File projectsTargetDir)
			throws AssignmentCompilationFailure {
		
		Queue<MavenProjectDescriptor> waitQueue = new LinkedBlockingQueue<>();
		int successfulCompilationCount = 0;
		
		for (File node : projectsTargetDir.listFiles()) {
			if (node.isDirectory()) {
				MavenProjectDescriptor descriptor;
				try {
					descriptor = new MavenProjectDescriptor(node);
					
					CompilationState state = processProject(compiler, descriptor);
					
					if(state == CompilationState.FAILURE) {
						waitQueue.add(descriptor);
					}
					else {
						successfulCompilationCount++;
					}
				} catch (MavenDescriptorException e) {
					throw new AssignmentCompilationFailure(node, "failed to generate the projet descriptor", e);
				}
			}
		}
		
		// We want to ensure that a header was generated, so that at least one project compiled.
		// TODO : make BaseReporter AutoCloseable.
		if(successfulCompilationCount > 0) {
			for(MavenProjectDescriptor failedProject : waitQueue) {
				try {
					MarkingConfiguration markingConfig = new MarkingConfigurationParser(config.getMarkingParametersFile()).parse();
					SonarAnalysisResult analysisResult = new SonarProjectAnalysis(failedProject, markingConfig, config.getPropertiesFile()).getAnalysisResults();
					reportFailedProjectsResults(failedProject, analysisResult);
				} catch (ReportFailureException e) {
					throw new AssignmentCompilationFailure(failedProject, "failed to write the report for the failed project", e);
				} catch (BadNotationConfigurationException e) {
					throw new AssignmentCompilationFailure(failedProject, "error when parsing the marking configuration file", e);
				} catch (SonarAnalysisException e) {
					throw new AssignmentCompilationFailure(failedProject, "sonar anlaysis failed", e);
				} catch (SonarProjectAnalysisIssueException e) {
					throw new AssignmentCompilationFailure(failedProject, "error when using the sonar analysis result", e);
				} catch (SonarPropertyParsingException e) {
					throw new AssignmentCompilationFailure(failedProject, "error when parsing the sonar property file", e);
				}
			}
		}
	}
	
	/*
	 * Utility method used to compile, execute and report the result of a given project
	 * using the given {@link AssignmentCompiler}.
	 * 
	 * @param compiler The {@link AssignmentCompiler} used to compile the project.
	 * @param descriptor The {@link IProjectDescriptor} representing the project to be
	 * compiled.
	 * 
	 * @return the compilation state of the project.
	 * 
	 * @throws AssignmentCompilationFailure
	 */
	private CompilationState processProject(AssignmentCompiler compiler, MavenProjectDescriptor descriptor) throws AssignmentCompilationFailure {
		try {
			progressHandler.update();

			String waitMsg = "Now analyzing " + descriptor.getProjectKey() + " (author : " + descriptor.getAuthor() + ")";
			jobLog.info(waitMsg);
			jobLog.info("Launching sonar analysis ...");
			progressHandler.refresh();
			launchSonarAnalysis(descriptor);
			jobLog.info("Compiling ...");
			progressHandler.refresh();
			InvocationOutput result = compileProject(compiler, descriptor);
			result.log(config.getVerboseLevel());
			logResult(result);
			
			if (result.getErrorReport().hasContent()) {
				// jobLog.info("New attempt to execute scheduled at the end ...");
				return CompilationState.FAILURE;
			}
			else {
				jobLog.info("Executing ...");
				progressHandler.refresh();
				TestResult testResults = executeProject(descriptor);
				
				MarkingConfiguration markingConfig = new MarkingConfigurationParser(config.getMarkingParametersFile()).parse();
				SonarAnalysisResult analysisResult = new SonarProjectAnalysis(descriptor, markingConfig, config.getPropertiesFile()).getAnalysisResults();

				reportResults(descriptor, analysisResult, markingConfig, testResults);
				
				return CompilationState.SUCCESS;
			}
			
		} catch (IllegalStateException e) {
			throw new AssignmentCompilationFailure(descriptor, "invalid program state", e);
		} catch (ReportFailureException e) {
			throw new AssignmentCompilationFailure(descriptor, "unable to write one or more report file", e);
		} catch (SonarAnalysisException e) {
			throw new AssignmentCompilationFailure(descriptor, "sonar analysis failed", e);
		} catch (SonarProjectAnalysisIssueException e) {
			throw new AssignmentCompilationFailure(descriptor, "error when using the sonar analysis result", e);
		} catch (SonarPropertyParsingException e) {
			throw new AssignmentCompilationFailure(descriptor, "error when parsing the sonar property file", e);
		} catch (BadNotationConfigurationException e) {
			throw new AssignmentCompilationFailure(descriptor, "error when parsing the marking configuration file", e);
		}
	}
	
	/*
	 * TODO : Should indicate sonar's analysis failure or success.
	 * Utility method to launch a sonar analysis.
	 * 
	 * @param descriptor The {@MavenProjectDescriptor} representing the project to be analyzed.
	 */
	private void launchSonarAnalysis(MavenProjectDescriptor descriptor) throws AssignmentCompilationFailure {
		try {
			MavenInvocationEngine invoker = new MavenInvocationEngine();
			invoker.setGoals("sonar:sonar");
			
			InvocationOutput result = invoker.execute(descriptor);
			result.log(config.getVerboseLevel());
			jobLog.info("Done !                              ");
			logResult(result);
		} catch (MavenInvocationEngineException e) {
			throw new AssignmentCompilationFailure(descriptor, "failed to launch the sonar analysis", e);
		}
	}
	
	/*
	 * Utility method used to compile a project and return all encountered errors, if any.
	 * 
	 * @param compiler The {@link AssignmentCompiler} used to compile the project.
	 * @param descriptor The {@link MavenProjectDescriptor} representing the project to be
	 * compiled.
	 * 
	 * @return a {@link MavenErrorReport} containing the errors.
	 * 
	 * @throws AssignmentCompilationFailure
	 */
	private InvocationOutput compileProject(AssignmentCompiler compiler, IProjectDescriptor descriptor) throws AssignmentCompilationFailure {
		return compiler.compile(descriptor.getDirectory());
	}
	
	/*
	 * Uility method used to execute a project, write the result reports, and return
	 * all encountered errors, if any.
	 * 
	 * @param descriptor The project descriptor used
	 *
	 * @return a {@link TestResult} containing the results obtained by the execution of the unit tests on the project.
	 * 
	 * @throws AssignmentCompilationFailure
	 */
	private TestResult executeProject(MavenProjectDescriptor descriptor) throws AssignmentCompilationFailure {
		MavenInvocationEngine invoker;
		List<String> invokerGoals = new ArrayList<>();
		invokerGoals.add("exec:java -Dexec.mainClass=" + RUNNER_CLASS.getName());
		
		InvocationOutput result;
		TestResult testResults = new TestResult();
		
		try {
			invoker = new MavenInvocationEngine();
			invoker.setGoals(invokerGoals);
			
			result = invoker.execute(descriptor);
			result.log(config.getVerboseLevel());
			logResult(result);

			testResults = TestResultSerializer.deserialize(result.getAutoMarkData());	

		} catch (MavenInvocationEngineException e) {
			throw new AssignmentCompilationFailure(descriptor.getDirectory(), "failed to build the Maven invoker", e);
		} catch (TestResultSerializationException e) {
			throw new AssignmentCompilationFailure(descriptor.getDirectory(), "failed to deserialize the produced results", e);
		}
				
		return testResults;
	}
	
	private void reportResults(MavenProjectDescriptor descriptor, SonarAnalysisResult analysisResult, MarkingConfiguration markingConfig, TestResult results) throws ReportFailureException {

		try(ResultReporter resultsReporter = new ResultReporter(descriptor, config.getOutputDirectory().getAbsolutePath(), new ResultFileHeaderTemplate(results.getMethodNames()), markingConfig);
			DetailsReporter detailReporter = new DetailsReporter(descriptor, config.getOutputDirectory().getAbsolutePath());
			) {		
			
			detailReporter.writeReport(results, analysisResult);
	
			resultsReporter.writeReport(results, analysisResult);
		} catch (IOException e) {
			throw new ReportFailureException("failed to write the report for the project", e);
		}
	}
	
	private void reportFailedProjectsResults(MavenProjectDescriptor descriptor, SonarAnalysisResult analysisResult) throws ReportFailureException {

		try(DummyResultReporter resultsReporter = new DummyResultReporter(descriptor, config.getOutputDirectory().getAbsolutePath());
			DetailsReporter detailReporter = new DetailsReporter(descriptor, config.getOutputDirectory().getAbsolutePath());
			){

			resultsReporter.writeReport(new TestResult(), new SonarAnalysisResult(descriptor));
			
			detailReporter.writeReport(new TestResult(), analysisResult);
		} catch (IOException e) {
			throw new ReportFailureException("failed to write the report of the (failed) project", e);

		}
	}

}
