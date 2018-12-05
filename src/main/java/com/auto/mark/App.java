package com.auto.mark;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.auto.mark.utils.InvalidCommandLineException;

import cmd.auto.mark.ApplicationConfiguration;
import cmd.auto.mark.FancyTextualProgressHandler;

/**
 * The main class of the application.
 * Will start the GUI or revert back to a more standard command line execution.
 */
public class App 
{
	private static final Logger mainLog = LogManager.getRootLogger();
	private static final Logger errorLog = LogManager.getLogger("errorLog");
	
	private App(){}
	
    public static void main( String[] args )
    {	
    	try {
        	ApplicationConfiguration config = new ApplicationConfiguration(args);

        	/* Display help if asked to
        	 * Else, if the application is to start in GUI mode, use the GUI launcher.
        	 * Else, only use the command line configuration and interface to start the analysis.    
        	 */
        	if(config.isHelp()) {
        		config.displayHelp();
        		return;
        	}
        	else if(config.isGui()) {
        		gui.auto.mark.App.main(args);
        	}
        	else {
        		FancyTextualProgressHandler progressHandler = new FancyTextualProgressHandler();
	        	CorrectionProcessManager manager = new CorrectionProcessManager(config, progressHandler);
	        	manager.execute();
	            mainLog.info( "Everything processed !" );
	            mainLog.info("See the results in " + config.getOutputDirectory().getAbsolutePath());
        	}
		} // We want the full message, so disable sonar exception (we really want to do that). 
    	catch (InvalidCommandLineException | AssignmentCompilationFailure e) { //NOSONAR
			mainLog.info("The execution ended with some errors (for more informations, see the logs)");
			errorLog.error(e.getFullMessage());
		}
    }
}
