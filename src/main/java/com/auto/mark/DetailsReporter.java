package com.auto.mark;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.auto.mark.runner.TestDescriptor;
import com.auto.mark.runner.TestResult;
import com.auto.mark.runner.TestState;
import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.IProjectDescriptor;
import com.auto.mark.utils.PathBuilder;

/**
 * A reporter class used to write the details report of the current execution.
 */
public class DetailsReporter extends BaseReporter {
	
	/**
	 * Create the reporter.
	 * 
	 * @param projectDescriptor The descriptor for the project we will report about.
	 * @param outputPath The output path.
	 * 
	 * @throws ReportFailureException
	 */
	public DetailsReporter(IProjectDescriptor projectDescriptor, String outputPath) throws ReportFailureException {		
		super(projectDescriptor, new PathBuilder(outputPath).append(CommonDefaultConfigurationValues.getDetailsFileName()).toString(), new DetailsFileHeaderTemplate());
	}
	
	@Override
	public void writeReport(TestResult results, SonarAnalysisResult analysisResult) throws ReportFailureException {
		List<String> details = Arrays.asList(new String[3]);
		
		try {
			writer.printRecord(super.projectDescriptor.getProjectKey() + ':' + super.projectDescriptor.getAuthor());
			
			for(TestDescriptor test : results) {
				details.set(0, test.getName());
				
				String detailString;
				if(test.getState() == TestState.FAILED) {
					detailString = test.getState() + " : " + test.getAdditionalMessage();
				}
				else {
					detailString = test.getState().toString();
				}
				details.set(1, detailString);
				
				writer.printRecord(details);
			}
			
			for(Entry<String, SonarProjectAnalysisIssue> entry : analysisResult) {
				SonarProjectAnalysisIssue issue = entry.getValue();
				
				details.set(0, issue.getRule());
				details.set(1, issue.getDescription());
				details.set(2, Integer.toString(issue.getNumberOfOccurrences()));
				
				writer.printRecord(details);
			}
		} catch (IOException e) {
			throw new ReportFailureException("Unable to write the report !", e);
		}
	}
}
