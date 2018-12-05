package com.auto.mark.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SonarPropertyParser {
	
	private SonarPropertyParser(){}
	
	public static Properties parse(File propertiesFile) throws SonarPropertyParsingException {
		Properties result = new Properties();
		try {
			result.load(new FileInputStream(propertiesFile));
		} catch (IOException e) {
			throw new SonarPropertyParsingException(e);
		}
		
		if(!result.containsKey(CommonDefaultConfigurationValues.getSonarPropertyUrlLabel())) {
			throw new SonarPropertyParsingException("no mandatory 'url' field found in the file !");
		}
		
		if(result.containsKey(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel()) 
			&& !result.containsKey(CommonDefaultConfigurationValues.getSonarPropertyLoginLabel())) {
			throw new SonarPropertyParsingException("missing login !");
		}
		
		return result;
	}
	
	public static Properties parse(String propertiesFileName) throws SonarPropertyParsingException {
		return parse(new File(propertiesFileName));
	}
}
