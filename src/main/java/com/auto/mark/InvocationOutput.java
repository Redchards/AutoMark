package com.auto.mark;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auto.mark.utils.IProjectDescriptor;
import com.auto.mark.utils.VerboseLevel;

/**
 * A class used as a base to store every information outputed by a build tool invocation, like maven for instance.
 * The class is able to differentiate between errors, warnings, infos, debugs and even a custom AutoMark data,
 * which we will use to pass our unit test results from the analyzed project back to our main program.
 * 
 * @see IReport
 * @see MavenInvocationOutput
 * @see MavenOutputListener
 */
public abstract class InvocationOutput {
	
	/* The descriptor of the project which produced this output. */
	private IProjectDescriptor projectDescriptor;
	
	/* The report for the errors. */
	protected IReport errorReport;
	/* The report for the warnings. */
	protected IReport warningReport;
	/* The report for the information. */
	protected IReport infoReport;
	/* The report for the debugs. */
	protected IReport debugReport;
	
	/* 
	 * The custom AutoMark data (usually the unit tests results)
	 * Only one can be present at a time, so it should be used sparingly.
	 */
	protected String autoMarkData;
	
	/*
	 * A simple field used to keep track of the message order, to restitute them
	 * in their original order of appearance when requested.
	 */
	private List<ReportMessageDescriptor> msgOrderKeeper;
	
	/* The application's main logger. */
	private static final Logger mainLog = LogManager.getRootLogger();
	
	/**
	 * Create the invocation output.
	 * 
	 * @param projectDescriptor The descriptor for the project that produced the output we will store.
	 */
	public InvocationOutput(IProjectDescriptor projectDescriptor) {
		this.projectDescriptor = projectDescriptor;

		msgOrderKeeper = new ArrayList<>();
	}
	
	/**
	 * Add an error to our output.
	 * @param err The error we want to add.
	 */
	public void addError(String err) {
		addMsgTo(errorReport, err);
	}
	
	/**
	 * Add a list of errors to our output.
	 * @param errList The errors we want to add.
	 */
	public void addAllErrors(List<String> errList) {
		addAllMsgTo(errorReport, errList);
	}
	
	/**
	 * Add a list of errors to our output.
	 * @param errList The errors we want to add.
	 */
	public void addAllErrors(String... errList) {
		addAllErrors(Arrays.asList(errList));
	}
	
	/**
	 * Add a warning to our output.
	 * @param err The warning we want to add.
	 */
	public void addWarning(String warn) {
		addMsgTo(warningReport, warn);
	}
	
	/**
	 * Add a list of warnings to our output.
	 * @param errList The warnings we want to add.
	 */
	public void addAllWarnings(List<String> warnList) {
		addAllMsgTo(warningReport, warnList);
	}
	
	/**
	 * Add a list of warnings to our output.
	 * @param errList The warnings we want to add.
	 */
	public void addAllWarnings(String... warnList) {
		addAllErrors(Arrays.asList(warnList));
	}
	
	/**
	 * Add an information to our output.
	 * @param err The information we want to add.
	 */
	public void addInfo(String info) {
		addMsgTo(infoReport, info);
	}
	
	/**
	 * Add a list of information to our output.
	 * @param errList The information we want to add.
	 */
	public void addAllInfos(List<String> infoList) {
		addAllMsgTo(infoReport, infoList);
	}
	
	/**
	 * Add a list of information to our output.
	 * @param errList The information we want to add.
	 */
	public void addAllInfos(String... infoList) {
		addAllErrors(Arrays.asList(infoList));
	}
	
	/**
	 * Add a debug to our output.
	 * @param err The debug we want to add.
	 */
	public void addDebug(String dbg) {
		addMsgTo(debugReport, dbg);
	}
	
	/**
	 * Add a list of debugs to our output.
	 * @param errList The debugs we want to add.
	 */
	public void addAllDebugs(List<String> dbgList) {
		addAllMsgTo(debugReport, dbgList);
	}
	
	/**
	 * Add a list of debugs to our output.
	 * @param errList The debugs we want to add.
	 */
	public void addAllDebugs(String... dbgList) {
		addAllErrors(Arrays.asList(dbgList));
	}
	
	/**
	 * Sets the specific AutoMark data. Will replace any previous value.
	 * @param data The data we will replace the old value with.
	 */
	public void setAutoMarkData(String data) {
		autoMarkData = data;
	}
	
	/**
	 * @return The specific AutoMark data.
	 */
	public String getAutoMarkData() {
		return autoMarkData;
	}

	/**
	 * @return The generated error report by the current project's execution.
	 */
	public IReport getErrorReport() {
		return errorReport;
	}
	
	/**
	 * @return The generated warning report by the current project's execution.
	 */
	public IReport getWarningReport() {
		return warningReport;
	}
	
	/**
	 * @return The generated error report by the current project's execution.
	 */
	public IReport getInfoReport() {
		return infoReport;
	}
	
	/**
	 * @return The generated debug report by the current project's execution.
	 */
	public IReport getDebugReport() {
		return debugReport;
	}
	
	/**
	 * @return The project descriptor.
	 */
	public IProjectDescriptor getProjectDescriptor() {
		return projectDescriptor;
	}
	
	/**
	 * Logs everything in order and in the right log level.
	 * 
	 * @param level The verbose level used.
	 * @see VerboseLevel
	 */
	public void log(VerboseLevel level) {
		
		for(ReportMessageDescriptor descriptor : msgOrderKeeper) {

			if((descriptor.getReportRef() == warningReport) && level.compareTo(VerboseLevel.LOW) >= 0) {
				mainLog.warn(descriptor.getReportRef().getMessage(descriptor.getIndex()));
			}
			else if((descriptor.getReportRef() == infoReport) && level.compareTo(VerboseLevel.HIGH) >= 0) {
				mainLog.info(descriptor.getReportRef().getMessage(descriptor.getIndex()));
			}
			else if((descriptor.getReportRef() == debugReport) && level.compareTo(VerboseLevel.ALL) >= 0) {
				mainLog.debug(descriptor.getReportRef().getMessage(descriptor.getIndex()));
			}
			else if(descriptor.getReportRef() == errorReport) {
				mainLog.error(descriptor.getReportRef().getMessage(descriptor.getIndex()));
			}
		}
	}
	
	/*
	 * Utility method used to add a single message to a report.
	 *  
	 * @param report
	 * @param msg
	 */
	private void addMsgTo(IReport report, String msg) {
		report.addMessage(msg);
		msgOrderKeeper.add(new ReportMessageDescriptor(report, report.size() - 1));
	}
	
	/*
	 * Utility method used to add mutliple messages to a report.
	 * 
	 * @param report
	 * @param msgList
	 */
	private void addAllMsgTo(IReport report, List<String> msgList) {
		report.addAllMessages(msgList);
		
		for(int i = 0; i < msgList.size(); i++) {
			msgOrderKeeper.add(new ReportMessageDescriptor(report, report.size() - 1));
		}
	}
	
	/*
	 * A class describing a report message by providing the report reference and the index of the message in
	 * the designated report.
	 */
	private class ReportMessageDescriptor {
		/*
		 * The report which holds the message.
		 */
		private IReport reportRef;
		
		/*
		 * The index of the message in the report.
		 */
		private int index;
		
		/**
		 * Builds a message descriptor using a report reference and an index.
		 * 
		 * @param reportRef The report which holds the message.
		 * @param index The index of the message in the report.
		 */
		public ReportMessageDescriptor(IReport reportRef, int index) {
			this.reportRef = reportRef;
			this.index = index;
		}
		
		/**
		 * @return The reference of the report which holds this message.
		 */
		public IReport getReportRef() {
			return reportRef;
		}
		
		/**
		 * @return The index of the message in the report.
		 */
		public int getIndex() {
			return index;
		}
	}

}
