package com.auto.mark;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author Loic
 * A fetcher used to fetch local directories. It effectively copies directory from one location to an other.
 */
public class LocalDirectoryFetcher implements FetcherInterface {
	
	@Override
	public void fetch(File srcDir, File targetDir) throws InvalidPathException, FetchingException {
		
		if(!targetDir.exists()) {
			targetDir.mkdir();
		}
		else {
			cleanDir(targetDir);
		}

		if(!srcDir.exists()) {
			throw new InvalidPathException(srcDir.toString());
		}
		
		try {
			FileUtils.copyDirectory(srcDir, targetDir);
		} catch (IOException e) {
			throw new FetchingException("Error when copying directory", e);
		}
	}
	
	@Override
	public void fetch(String srcPath, String targetPath) throws InvalidPathException, FetchingException {
		fetch(new File(srcPath), new File(targetPath));
	}
	
	private void cleanDir(File targetDir) throws FetchingException {
		FileFilter filter = path -> !path.getName().equals(MAVEN_CONFIG_FILE_NAME);

		for(File elem : targetDir.listFiles(filter)) {
			if(elem.isFile()){
				if(!elem.delete()) {
					throw new FetchingException("failed to clean the target directory when fetching the sources !");
				}
			}
			else {
				try {
					FileUtils.deleteDirectory(elem);
				} catch (IOException e) {
					throw new FetchingException("error when cleaning the directory", e);
				}
			}
		}
	}
}
