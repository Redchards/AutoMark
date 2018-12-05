package com.auto.mark;

import java.io.File;

/**
 * The fetcher interface used to fetch directories from local or external sources.
 */
public interface FetcherInterface {
	public static final String MAVEN_CONFIG_FILE_NAME = "pom.xml";
	
	/**
	 * A simple utility method used to copy a directory's content from one to
	 * an other.
	 * 
	 * @param srcDir The source directory from which we will copy the data.
	 * @param targetDir The target directory in which the data will be copied.
	 * 
	 * @throws InvalidPathException
	 * @throws FetchingException
	 */
	public void fetch(File srcDir, File targetDir) throws InvalidPathException, FetchingException;
	
	/**
	 * A simple utility method used to copy a directory's content from one to
	 * an other.
	 * 
	 * @param srcPath The source directory's path from which we will copy the data.
	 * @param targetPath The target directory's path in which the data will be copied.
	 * 
	 * @throws InvalidPathException
	 * @throws FetchingException
	 */
	public void fetch(String srcPath, String targetPath) throws InvalidPathException, FetchingException;
}
