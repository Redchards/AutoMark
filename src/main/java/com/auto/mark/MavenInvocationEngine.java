package com.auto.mark;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;

import com.auto.mark.utils.MavenProjectDescriptor;

/**
 * This class ought to be used in order to make Maven Invoker's usage easier for
 * the class {@link AssignementCompiler}. Its main goal is to catch errors related to
 * projects that are compiled. It can be used for the analysis of one project at
 * a time but it can easily switch to another targeted project.
 */

public class MavenInvocationEngine {

	/**
	 * This Maven invocation engine's listener used to catch errors listed by
	 * Maven.
	 */
	private MavenOutputListener outputListener;

	/**
	 * This list contains all the goals used for this invocation.
	 */
	private List<String> goalList;

	/**
	 * The actual Maven invoker used to compile projects.
	 */
	private Invoker mavenInvoker;


	public MavenInvocationEngine() throws MavenInvocationEngineException {
		this(new ArrayList<>());
	}

	/**
	 * Creates a Maven invocation instance for a specific project directory with
	 * a given list of goals.
	 * 
	 * @param projectDir
	 *            The project used for this Maven invocation
	 * @param goalList
	 *            The list of goals run in this invocation
	 * @throws MavenInvocationEngineException 
	 */
	public MavenInvocationEngine(List<String> goalList) throws MavenInvocationEngineException {
		this.goalList = new ArrayList<>(goalList);

		mavenInvoker = new DefaultInvoker();
		outputListener = new MavenOutputListener();
	}

	/**
	 * Creates a Maven invocation instance for a specific project directory with
	 * a given list of goals.
	 * 
	 * @param projectDir
	 *            The project used for this Maven invocation
	 * @param goalList
	 *            The list of goals run in this invocation
	 * @throws MavenInvocationEngineException 
	 */
	public MavenInvocationEngine(String... goals) throws MavenInvocationEngineException {
		this(Arrays.asList(goals));
	}

	/**
	 * Execute this Maven invocation that targets a given Maven project
	 * 
	 * @throws MavenInvocationEngineException 
	 */
	public InvocationOutput execute(MavenProjectDescriptor projectDescriptor) throws MavenInvocationEngineException {

		try {
			if(projectDescriptor == null) {
				throw new MavenInvocationEngineException("the project directory was not set");
			}
			InvocationRequest request = new DefaultInvocationRequest();
			request.setGoals(goalList);
			request.setPomFile(new File(projectDescriptor.getPomPath()));

			outputListener.listenTo(projectDescriptor, request);

			mavenInvoker.execute(request);
		} catch (MavenInvocationException e) {
			throw new MavenInvocationEngineException("failed to invoke maven", e);
		}

		return outputListener.getMavenOutput();
	}
	
	/**
	 * Set a new goal list to this Maven invocation's list of goals.
	 * 
	 * @param goalList
	 *            The goals to set
	 */
	public void setGoals(List<String> goalList) {
		this.goalList = new ArrayList<>(goalList);
	}
	
	/**
	 * Set a new goal list to this Maven invocation's list of goals.
	 * 
	 * @param goalList
	 *            The goals to set
	 */
	public void setGoals(String... goalList) {
		this.goalList = Arrays.asList(goalList);
	}

	/**
	 * Appends a new goal to this Maven invocation's list of goals.
	 * 
	 * @param newGoal
	 *            The new goal to add
	 */
	public void addGoal(String newGoal) {
		goalList.add(newGoal);
	}

	/**
	 * Appends a list of goals to this Maven invocation's list of goals.
	 * 
	 * @param goalList
	 *            The list of goals to append from.
	 */
	public void addAllGoals(List<String> goalList) {

		this.goalList.addAll(goalList);
	}

	/**
	 * Appends a list of goals to this Maven invocation's list of goals.
	 * 
	 * @param goalList
	 *            The list of goals to append from.
	 */
	public void addAllGoals(String... goalList) {

		this.goalList.addAll(Arrays.asList(goalList));
	}

	/**
	 * Empties the list of goals of this MavenInvocationEngine instance.
	 */
	public void clearGoals() {
		goalList.clear();
	}

}
