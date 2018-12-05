package cmd.auto.mark;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auto.mark.IApplicationConfiguration;
import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.InvalidCommandLineException;
import com.auto.mark.utils.VerboseLevel;
import com.beust.jcommander.JCommander;

/**
 * A simple command line helper utility used to configure the project when started.
 * in command line.
 */
public class ApplicationConfiguration implements IApplicationConfiguration {

	private final File projectsDir;
	private final File testsDir;
	private final File sonarPropertiesFile;
	private final File markingParametersFile;
	private final File outputDir;
	private final VerboseLevel verboseLevel;
	private final boolean isGui;
	private final boolean isHelp;
	
	private JCommander cmdParser;
	
	private static final Logger mainLog = LogManager.getRootLogger();
	
	/**
	 * Create the helper using the command line.
	 * @param args the command line to be used.
	 * @throws InvalidCommandLineException
	 */
	public ApplicationConfiguration(String[] args) throws InvalidCommandLineException {
		CommandLineConfiguration cmdConfig = new CommandLineConfiguration();
		
		cmdParser = new JCommander(cmdConfig, args);
		
		isHelp = cmdConfig.help;
		isGui = cmdConfig.isGui;
		verboseLevel = cmdConfig.verboseLevel; // Can only be set by command line.

		
		if(isHelp || isGui) {
			projectsDir = null;
			testsDir = null;
			sonarPropertiesFile = null;
			markingParametersFile = null;
			outputDir = null;
		}
		else {
			
			String projectsPath = cmdConfig.projectsPath;
			String testsPath = cmdConfig.testsPath;
			
			if(projectsPath == null) {
				throw new InvalidCommandLineException("The path to the projects directory wasn't set !");
			}
			else if(testsPath == null) {
				throw new InvalidCommandLineException("The path to the tests directory wasn't set !");
			}
				
			projectsDir = new File(projectsPath);
			testsDir = new File(testsPath);
			
			final String LOG_MSG_PART1 = "Using the default sonar properties file : ";
			final String LOG_MSG_PART2 = " (full path : ";
			final String LOG_MSG_PART3 = ") !";
				
			if(cmdConfig.sonarPropertiesPath == null) {
				String defaultName = CommonDefaultConfigurationValues.getDefaultSonarPropertiesFileName();
				
				sonarPropertiesFile = new File(defaultName);
				
				mainLog.warn(LOG_MSG_PART1 + defaultName 
						+ LOG_MSG_PART2 + sonarPropertiesFile.getAbsolutePath() + LOG_MSG_PART3);
			}
			else {
				sonarPropertiesFile = new File(cmdConfig.sonarPropertiesPath);
			}
			
			if(cmdConfig.markingParametersPath == null) {
				String defaultName = CommonDefaultConfigurationValues.getDefaultMarkingParametersFileName();
					
				markingParametersFile = new File(defaultName);
					
				mainLog.warn(LOG_MSG_PART1 + defaultName 
						+ LOG_MSG_PART2 + markingParametersFile.getAbsolutePath() + LOG_MSG_PART3);
			}
			else {
				markingParametersFile = new File(cmdConfig.markingParametersPath);
			}
			
			if(cmdConfig.outputPath == null) {
				String defaultName = CommonDefaultConfigurationValues.getDefaultOutputDirectoryPath();
				
				outputDir = new File(defaultName);
				
				mainLog.warn(LOG_MSG_PART1 + defaultName 
						+ LOG_MSG_PART2 + outputDir.getAbsolutePath() + LOG_MSG_PART3);
			}
			else {
				outputDir = new File(cmdConfig.outputPath);
			}
						
			checkFilesExist();
		}
	}
	
	/**
	 * @return the directory where the projects are located.
	 */
	@Override
	public File getProjectsDir() {
		return projectsDir;
	}
	
	/**
	 * @return the directory where the tests are located.
	 */
	@Override
	public File getTestsDir() {
		return testsDir;
	}

	/**
	 * @return the file containing the SonarQube properties.
	 */
	@Override
	public File getPropertiesFile() {
		return sonarPropertiesFile;
	}

	/**
	 * @return the file containing the marking parameters.
	 */
	@Override
	public File getMarkingParametersFile() {
		return markingParametersFile;
	}
	
	/**
	 * @return the output directory.
	 */
	@Override
	public File getOutputDirectory() {
		return outputDir;
	}
	
	/**
	 * @return the verbose level.
	 */
	@Override
	public VerboseLevel getVerboseLevel() {
		return verboseLevel;
	}
	
	/**
	 * @return true if the application is to be started in GUI form, false otherwise.
	 */
	@Override
	public boolean isGui() {
		return isGui;
	}
	
	/**
	 * @return true if the command line need to show the help, false otherwise.
	 */
	public boolean isHelp() {
		return isHelp;
	}
	
	/**
	 * Display the command line help.
	 */
	public void displayHelp() {
		cmdParser.usage();
	}
	
	/* NOTE : it is perfectly normal that we do not check the existence of the output directory, we will create it
	 * if it does not exist !
	 */
	private void checkFilesExist() throws InvalidCommandLineException {
		final String NOT_EXIST_MSG = "' does not exist !";
		
		if(!projectsDir.exists() || !projectsDir.isDirectory()) {
			throw new InvalidCommandLineException("The path of the projects directory is invalid, the directory '" + projectsDir.getAbsolutePath() + NOT_EXIST_MSG);
		}
		else if(!testsDir.exists() || !testsDir.isDirectory()) {
			throw new InvalidCommandLineException("The path of the tests directory is invalid, the directory '" + testsDir.getAbsolutePath() + NOT_EXIST_MSG);
		}
		else if(!sonarPropertiesFile.exists() || !sonarPropertiesFile.isFile()) {
			throw new InvalidCommandLineException("The path of the sonar properties file is invalid, the file '" + sonarPropertiesFile.getAbsolutePath() + NOT_EXIST_MSG);
		}
		else if(!markingParametersFile.exists() || !markingParametersFile.isFile()) {
			throw new InvalidCommandLineException("The path of the marking parameters file is invalid, the file '" + markingParametersFile.getAbsolutePath() + NOT_EXIST_MSG);
		}
	}

}