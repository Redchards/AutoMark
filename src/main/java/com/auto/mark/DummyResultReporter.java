package com.auto.mark;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.auto.mark.runner.TestResult;
import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.IProjectDescriptor;
import com.auto.mark.utils.PathBuilder;

/** A dummy result reporter used to report the project for which the compilation
 * failed, and a such, can't write their own report.
 * @see CorrectionProcessManager
 */
public class DummyResultReporter extends BaseReporter {
	
	private static final double DUMMY_FACTOR = 1.0;
	
	/* TODO : Should implement a constructor without header template in the BaseReporter
	 * It's not an absolute necessity though, it will fail gracefuly anyway if the file was not already created. 
	 */
	/**
	 * Simple constructor using a project descriptor and and output path to build the reporter.
	 * 
	 * @param projectDescriptor
	 * @param outputPath
	 * @throws ReportFailureException
	 */
	public DummyResultReporter(IProjectDescriptor projectDescriptor, String outputPath) throws ReportFailureException {
		super(projectDescriptor, new File(new PathBuilder(outputPath).append(CommonDefaultConfigurationValues.getResultsFileName()).toString()), new CsvHeaderTemplate());
	}

	@Override
	public void writeReport(TestResult testResults, SonarAnalysisResult analysisResult) throws ReportFailureException {
		String[] tmp = new String[header.size()];
		Arrays.fill(tmp, "0");
		List<String> results = Arrays.asList(tmp);
		
		results.set(0, projectDescriptor.getProjectKey() + ':' + projectDescriptor.getAuthor());
		results.set(1, CompilationState.FAILURE.toString());
		results.set(header.size() - 2, Double.toString(DUMMY_FACTOR));
		
		try {
			writer.printRecord(results);
		} catch (IOException e) {
			throw new ReportFailureException(projectDescriptor.getProjectKey(), e);
		}
	}
}
