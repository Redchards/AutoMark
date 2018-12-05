package cmd.auto.mark;

import java.util.ArrayList;
import java.util.List;

import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.VerboseLevel;
import com.beust.jcommander.Parameter;

/**
 * The command line configuration of the main application.
 */
public class CommandLineConfiguration {
	@Parameter
	private List<String> parameters = new ArrayList<>();
	
	@Parameter(names = {"-p", "--projects"}, description = "The path of the root directory where the projects are located.")
	public String projectsPath;
	
	@Parameter(names = {"-t", "--tests"}, description = "The path of the directory where the tests are located.")
	public String testsPath;
	
	@Parameter(names = {"-h", "--help"}, description = "Display this help.", help = true)
	public boolean help = false;

	@Parameter(names = {"-s", "--sonar-properties"}, description = "The path of the sonar properties file.")
	public String sonarPropertiesPath;

	@Parameter(names = {"-m", "--marking-parameters"}, description = "The path of the marking parameters file.")
	public String markingParametersPath;
	
	@Parameter(names = {"-o", "--output"}, description = "The path where the resulting files will be produced.")
	public String outputPath;

	@Parameter(names = {"-g", "--gui"}, description = "Indicate wether the application should start in GUI mode.")
	public boolean isGui;
	
	@Parameter(names = {"-v", "--verbose"}, description = "Select the verbose level.")
	public VerboseLevel verboseLevel = CommonDefaultConfigurationValues.getDefaultVerboseLevel();
}
