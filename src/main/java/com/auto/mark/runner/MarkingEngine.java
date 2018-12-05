package com.auto.mark.runner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.auto.mark.annotation.MarkedTest;
import com.auto.mark.utils.TestResultSerializationException;
import com.auto.mark.utils.TestResultSerializer;
import com.auto.mark.utils.CommonDefaultConfigurationValues;
import com.auto.mark.utils.MavenDescriptorException;

/**
 * @author Loic
 * A class used to mark maven projects.
 * Currently it is using the "reflections" library
 * (https://github.com/ronmamo/reflections)
 * But it could be using a more lightweight custom implementation as suggested here :
 * http://stackoverflow.com/questions/862106/how-to-find-annotated-methods-in-a-given-package
 * 
 * This could be useful too :
 * https://github.com/rmuller/infomas-asl
 * 
 * However, I do not see the necessity of such thing right now.
 * 
 * Right now, this code do not support any kind of fixtures or test suite, it has a purely demonstrative
 * purpose. It do not support generic arguments either.
 * 
 * It's certainly not the most efficient thing either. With static methods it's fine, but if not, it needs to 
 * dynamically create a new instance of the class via reflection, a quite costly thing to do.
 * So I decided to only support static methods as we do not want to implement fixtures for now.
 * Last but not the least, for each project, we recompile the test, which is wasteful.
 * Ideally, the test compilation should not be done here !
 * 
 * https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Method.html
 */

public class MarkingEngine {

	private Reflections reflections;
	private Set<Method> markingMethods;
		
	// Should refactor that, too much code duplication !!!
	public MarkingEngine() {			
		reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forManifest())
				.setScanners(new MethodAnnotationsScanner()));
		
		// 	ClassPathHelper.forClassPath don't seem to do anything ...			
		
		// System.out.println(reflections.getAllTypes().size());
		markingMethods = reflections.getMethodsAnnotatedWith(MarkedTest.class);
	}
	
	public MarkingEngine(String packageName) {				
		reflections = new Reflections(new ConfigurationBuilder()
				.setUrls(ClasspathHelper.forPackage(packageName))
				.setScanners(new MethodAnnotationsScanner()));
		
		markingMethods = reflections.getMethodsAnnotatedWith(MarkedTest.class);
	}
	
	/**
	 * TODO : Check if result is positive.
	 * TODO : method.invoke() only throws invokation error, we should fix that and provide more meaningful errors.
	 * @throws ResultsProductionException 
	 * @throws MavenDescriptorException 
	 */
	public TestResult run() throws ResultsProductionException, MavenDescriptorException {		
		
		TestResult results = new TestResult();

		for(Method method : markingMethods) {			
			try {
				method.invoke(null); // Only possible to put "null" here because the method is guaranteed to be static.
				results.addResult(method, TestState.PASSED);
			}
			// We disable sonar analysis on two lines here, because sonar really wants us to log the exception using
			// log4j for example, but here, these exceptions are just used to tell if the test passed or not !
			catch(InvocationTargetException e) { // NOSONAR
				String errorMessage = e.getCause().getMessage();
				if(errorMessage == null) {
					errorMessage = "no error message provided";
				}
				
				results.addResult(method, TestState.FAILED, errorMessage);
			} catch (IllegalArgumentException | IllegalAccessException e) { // NOSONAR
				results.addResult(method, TestState.FAILED, "failed to invoke the method");
			}
		}

		try {
			outputResults(results);
		} catch (TestResultSerializationException e) {
			throw new ResultsProductionException("result serialization failed !", e);
		}
		
		return results;
	}
	
	private void outputResults(TestResult results) throws TestResultSerializationException {
		System.out.println(CommonDefaultConfigurationValues.getAutoMarkDataOutputLabel() + TestResultSerializer.serialize(results));
	}
}