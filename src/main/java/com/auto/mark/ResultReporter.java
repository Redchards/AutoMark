package com.auto.mark;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.auto.mark.runner.TestDescriptor;
import com.auto.mark.runner.TestResult;
import com.auto.mark.runner.TestState;
import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.IProjectDescriptor;
import com.auto.mark.utils.PathBuilder;

/**
 * The class used to report unit test results and generate the final mark of
 * our current marked project.
 */
public class ResultReporter extends BaseReporter {

	private static final double DEFAULT_COEFF = 20.0;

	private List<String> results;
	
	private MarkingConfiguration markingConfig;
	

	/**
	 * TODO : Allow for a different CSV format and charset !
	 * 
	 * @param projectDescriptor the project descriptor for which we will write the report.
	 * @param outputPath the output path to where we will write the report.
	 * @param template the header template of the report.
	 * @throws ReportFailureException
	 */
	public ResultReporter(IProjectDescriptor projectDescriptor, String outputPath, ResultFileHeaderTemplate template, MarkingConfiguration markingConfig)
			throws ReportFailureException {
		
		super(projectDescriptor, new PathBuilder(outputPath).append(CommonDefaultConfigurationValues.getResultsFileName()).toString(), template);

		this.markingConfig = markingConfig;
		
		String[] tmp = new String[header.size()];
		Arrays.fill(tmp, "0");
		results = Arrays.asList(tmp);
		results.set(0, projectDescriptor.getProjectKey() + ':' + projectDescriptor.getAuthor());
		results.set(1, CompilationState.SUCCESS.toString());
	}

	private void addResults(TestResult testResult) throws ReportFailureException{
		for (TestDescriptor test : testResult) {
			
			if (!headerMap.containsKey(test.getName())) {
				throw new ReportFailureException("Method not found !");
			} else if ("factor".equals(test.getName()) || ("finalResult".equals(test.getName()))) {
				throw new ReportFailureException("Method name invalid, 'factor' and 'finalResult' are reserved !");
			}
			
			if(test.getState() == TestState.PASSED) {
				results.set(headerMap.get(test.getName()), Double.toString(test.getValue()));
			}
			else {
				results.set(headerMap.get(test.getName()), "0");
			}
		}
	}

	/**
	 * TODO : Add a check to see if we missed some methods.
	 * 
	 * @param testResults The results of the marked tests execution.
	 * @param analysisResult The result of the SonarQube analysis.
	 * 
	 * @throws ReportFailureException
	 */
	@Override
	public void writeReport(TestResult testResults, SonarAnalysisResult analysisResult) throws ReportFailureException {
		
		try {
			addResults(testResults);
			
			double factor = CodeQualityFactorGenerator.generate(markingConfig, analysisResult);

			results.set(results.size() - 2, Double.toString(factor));
			
			// Warning : due to the floating point imprecision, this may not be
			// as accurate as expected.
			// However, it should go nicely if everything is well rounded.
			double finalResult = 0.0;
			if ((testResults.getTotalObtainable()) < 0 || (testResults.getTotalObtainable() > 0)) {
				finalResult = (testResults.getPointsObtained() / testResults.getTotalObtainable()) * factor
						* DEFAULT_COEFF;
			}

			results.set(results.size() - 1, Double.toString(finalResult));

			writer.printRecord(results);
		} catch (IOException e) {
			throw new ReportFailureException(projectDescriptor.getProjectKey(), e);
		}
	}
}
