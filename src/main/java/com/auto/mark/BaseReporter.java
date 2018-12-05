package com.auto.mark;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auto.mark.runner.TestResult;
import com.auto.mark.utils.IProjectDescriptor;

/**
 * The class every reporter class is based on.
 * It provided basic generic features to create a reporter.
 */
public abstract class BaseReporter implements AutoCloseable {
	private static final CSVFormat DEFAULT_CSV_FORMAT = CSVFormat.DEFAULT.withRecordSeparator('\n');
	
	protected File reportFile;
	protected List<String> header;
	protected Map<String, Integer> headerMap;
	protected IProjectDescriptor projectDescriptor;
	
	private FileWriter fileWriter;
	protected CSVPrinter writer;
	
	private static final Logger mainLog = LogManager.getRootLogger();

	/**
	 * Create a simple BaseReporter
	 * 
	 * @param projectDescriptor The project descriptor of the project we want to report.
	 * @param reportFile The report file used.
	 * @param template The header template which will be used to generate the header, should it not exist.
	 * 
	 * @throws ReportFailureException
	 */
	public BaseReporter(IProjectDescriptor projectDescriptor, File reportFile, CsvHeaderTemplate template) throws ReportFailureException {
		header = new ArrayList<>();
				
		this.reportFile = reportFile;
		this.projectDescriptor = projectDescriptor;
		
		if(!reportFileExistsAndNotEmpty()) {
			if (!reportFile.getParentFile().exists()) {
				reportFile.getParentFile().mkdir();
			}
			try {
				if(reportFile.createNewFile()) {
					mainLog.debug("Report file '" + reportFile.getAbsolutePath() + "' already exists, skipping creation.");
				}
			} catch (IOException e) {
				throw new ReportFailureException(projectDescriptor.getProjectKey() + ". Unable to create the report file !", e);
			}
			
			HeaderWriter headerWriter = new HeaderWriter();
			headerWriter.writeHeader(template);
		}
		
		try(FileReader parserReader = new FileReader(reportFile);
			CSVParser parser = new CSVParser(parserReader, DEFAULT_CSV_FORMAT.withHeader())) {
			
			this.fileWriter = new FileWriter(reportFile, true);
			this.writer = new CSVPrinter(fileWriter, DEFAULT_CSV_FORMAT);
			
			headerMap = parser.getHeaderMap();
			header = Arrays.asList(new String[headerMap.size()]);
			
			for (Entry<String, Integer> entry : headerMap.entrySet()) {
				header.set(entry.getValue(), entry.getKey());
			}

		} catch (FileNotFoundException e) {
			throw new ReportFailureException(projectDescriptor.getProjectKey(), e);
		} catch (IOException e) {
			throw new ReportFailureException(projectDescriptor.getProjectKey(), e);
		}
	}
	
	/**
	 * Create a simple BaseReporter.
	 * 
	 * @param projectDescriptor The project descriptor of the project we want to report.
	 * @param reportFilePath The path of the report file used.
	 * @param template The header template which will be used to generate the header, should it not exist.
	 * 
	 * @throws ReportFailureException
	 */
	public BaseReporter(IProjectDescriptor projectDescriptor, String reportFilePath, CsvHeaderTemplate template) throws ReportFailureException {
		this(projectDescriptor, new File(reportFilePath), template);
	}
	
	/*
	 * @return whether the report file exists and is not empty.
	 */
	private boolean reportFileExistsAndNotEmpty() {
		return reportFile.length() != 0;
	}
	
	/**
	 * Write the report.
	 * 
	 * @param testResults The test results that will be used.
	 * @param analysisResult The analysis results that will be used.
	 * 
	 * @throws ReportFailureException
	 */
	public abstract void writeReport(TestResult testResults, SonarAnalysisResult analysisResult) throws ReportFailureException;
	
	/**
	 * Closes the reporter.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		writer.flush();
		writer.close();
		fileWriter.close();
	}
	
	/**
	 * A simple utility class to write the CSV header in the report file.
	 */
	public class HeaderWriter {
		public HeaderWriter() {}
		
		/**
		 * Write the header following the given {@link CsvHeaderTemplate}
		 * 
		 * @param template The header template that will be used in order to generate our header.
		 * 
		 * @throws ReportFailureException
		 */
		public void writeHeader(CsvHeaderTemplate template) throws ReportFailureException {
			try(CSVPrinter writer = new CSVPrinter(new FileWriter(BaseReporter.this.reportFile, true), DEFAULT_CSV_FORMAT)) {
				writer.printRecord(template);
			} catch (IOException e) {
				throw new ReportFailureException(BaseReporter.this.projectDescriptor.getProjectKey(), e);
			}
		}
	}
}
