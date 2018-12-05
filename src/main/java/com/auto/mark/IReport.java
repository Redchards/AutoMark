package com.auto.mark;

import java.util.List;

import com.auto.mark.utils.MavenProjectDescriptor;

/**
 * An interface common to every build tool message report.
 */
public interface IReport {

	/**
	 * Adds a new error described by a String to the error list.
	 * 
	 * @param newError
	 *            The new error to add to the error list.
	 */
	public void addMessage(String newError);

	/**
	 * Appends the error list with another error list.
	 * 
	 * @param errorList
	 *            The error list to append from.
	 */
	public void addAllMessages(List<String> errorList);

	/**
	 * Empties the error list.
	 */
	public void clearMessages();
	/**
	 * @return The error list.
	 */
	public List<String> getMessageList();
	
	/**
	 * 
	 * @param index The index of the message in the index list.
	 * @return the message at the index.
	 */
	public String getMessage(int index);
	
	/**
	 * @return the size (in line number) of the report.
	 */
	public int size();

	/**
	 * @return The project this MavenErrorReport instance is about refered by a
	 *         MavenProjectDescriptor
	 */
	public MavenProjectDescriptor getProjectDescriptor();
	
	/**
	 * @return true if the error list is empty, false otherwise.
	 */
	public boolean isEmpty();
	
	/**
	 * @return true if the report contains some errors, false otherwise
	 */
	public boolean hasContent();
}
