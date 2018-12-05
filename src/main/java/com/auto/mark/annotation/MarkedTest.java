package com.auto.mark.annotation;

/**
 * The annotation class used to write unit tests.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Make sure we can use this at the runtime via reflection.
@Retention(RetentionPolicy.RUNTIME)
// Restrict annotation usage to methods.
@Target(ElementType.METHOD)
public @interface MarkedTest {
	public double value() default 0.0;
}
