package gui.auto.mark;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.SonarPropertyParser;
import com.auto.mark.utils.SonarPropertyParsingException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SonarSettingsController implements ISettingsController {
	@FXML
	TextField sonarUrl;

	@FXML
	TextField sonarLogin;
	
	@FXML
	TextField sonarPassword;
	
	@FXML
	TextField sonarOrganization;
	
	Properties properties;
	File propertiesFile;
	
	private String origSonarUrl;
	private String origSonarLogin;
	private String origSonarPassword;
	private String origSonarOrganization;
	
	private Logger errorLog = LogManager.getLogger("errorLog");
	private static final Logger mainLog = LogManager.getRootLogger();
	
	public SonarSettingsController() {
		properties = new Properties();
		propertiesFile = null;
		
		origSonarUrl = null;
		origSonarLogin = null;
		origSonarPassword = null;
		origSonarOrganization = null;
	}
	
	@FXML
	public void initialize() {
		propertiesFile = new File(CommonDefaultConfigurationValues.getDefaultSonarPropertiesFileName());
		
		if(!propertiesFile.exists()) {
			try {
				if(propertiesFile.createNewFile()) {
					mainLog.debug("Sonar property file created successfully");
				}
			} catch (IOException e) {
				errorLog.error(e);
				Platform.runLater(() -> ErrorDisplay.displayError("Sonar settings error", e.getMessage()));	
			}
		}
		else {
			try {
				properties = SonarPropertyParser.parse(propertiesFile);
				
				origSonarUrl = properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyUrlLabel());
				sonarUrl.setText(origSonarUrl);
				
				origSonarLogin = properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyLoginLabel());
				sonarLogin.setText(origSonarLogin);
				
				if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel())) {
					origSonarPassword = properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel());
					sonarPassword.setText(origSonarPassword);
				}
				if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel())) {
					origSonarOrganization = properties.getProperty(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel());
					sonarOrganization.setText(origSonarOrganization);
				}
			} catch (SonarPropertyParsingException e) {
				errorLog.error(e);
				Platform.runLater(() -> ErrorDisplay.displayError("Sonar settings error", e.getMessage()));	
			}
		}
	}
	
	@Override
	public void save() {
		origSonarUrl = sonarUrl.getText();
		properties.setProperty(CommonDefaultConfigurationValues.getSonarPropertyUrlLabel(), sonarUrl.getText());
		
		origSonarLogin = sonarLogin.getText();
		properties.setProperty(CommonDefaultConfigurationValues.getSonarPropertyLoginLabel(), sonarLogin.getText());
		
		if(!sonarPassword.getText().isEmpty()) {
			origSonarPassword = sonarPassword.getText();
			properties.setProperty(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel(), sonarPassword.getText());
		}
		else if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel())) {
			properties.remove(CommonDefaultConfigurationValues.getSonarPropertyPasswordLabel());
		}
		
		if(!sonarOrganization.getText().isEmpty()) {
			origSonarOrganization = sonarOrganization.getText();
			properties.setProperty(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel(), sonarOrganization.getText());
		}
		else if(properties.containsKey(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel())) {
			properties.remove(CommonDefaultConfigurationValues.getSonarPropertyOrganizationLabel());
		}
		
		try {
			properties.store(new FileOutputStream(propertiesFile), "Auto-generated file from AutoMark's GUI.");
		} catch (IOException e) {
			errorLog.error(e);
			Platform.runLater(() -> ErrorDisplay.displayError("Sonar settings error", e.getMessage()));	
		}
	}
	
	@Override
	public boolean wasModified() {
		if (!sonarUrl.getText().equals(origSonarUrl) || !sonarLogin.getText().equals(origSonarLogin)) {
			if ((sonarPassword.getText().isEmpty() && origSonarPassword == null) || (sonarPassword.getText().equals(origSonarPassword))) {
				return false;
			}
			else if ((sonarOrganization.getText().isEmpty() && origSonarOrganization == null) || (sonarOrganization.getText().equals(origSonarOrganization))){
				return false;
			}
			else {
				return true;
			}
		}
		
		return false;
	}
}
