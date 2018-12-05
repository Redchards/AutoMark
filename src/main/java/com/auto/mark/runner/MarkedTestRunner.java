package com.auto.mark.runner;

import com.auto.mark.utils.InvalidCommandLineException;
import com.auto.mark.utils.MavenDescriptorException;

/**
 * TODO : Refactor, too much exception thrown
 * The class replacing the main in the projects to be marked.
 * Will start the marking process and generate the report.
 */
public class MarkedTestRunner 
{	
    public static void main( String[] args ) throws ResultsProductionException, MavenDescriptorException, InvalidCommandLineException
    {
    	// TODO : Pass the configuration file as a parameter of the command line.
        System.out.println( "Running tests !!!" );
        MarkingEngine engine = new MarkingEngine();
        TestResult res = engine.run();
        
        System.out.println("coeff : " + res.getPointsObtained());
    }
}
