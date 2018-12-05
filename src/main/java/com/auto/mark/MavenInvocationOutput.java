package com.auto.mark;

import com.auto.mark.utils.MavenProjectDescriptor;

public class MavenInvocationOutput extends InvocationOutput {
	public MavenInvocationOutput(MavenProjectDescriptor projectDescriptor) {
		super(projectDescriptor);
		
		this.errorReport = new MavenReport(projectDescriptor);
		this.warningReport = new MavenReport(projectDescriptor);
		this.infoReport = new MavenReport(projectDescriptor);
		this.debugReport = new MavenReport(projectDescriptor);
		this.autoMarkData = "";
	}
}
