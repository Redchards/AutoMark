package com.auto.mark.utils;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class PathBuilderTest {
	
	@Test
	public void simpleOnePartPathWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.append("foo");
		
		Assert.assertEquals("foo", builder.toString());
	}
	
	@Test
	public void simpleOnePartPathWithInitTest() {
		PathBuilder builder = new PathBuilder("foo");
		
		Assert.assertEquals("foo", builder.toString());
	}
	
	@Test
	public void simpleTwoPartPathWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.append("foo").append("bar");
		
		String expectedPath = "foo" + File.separator + "bar";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleTwoPartPathWithInitTest() {
		PathBuilder builder = new PathBuilder("foo");
		builder.append("bar");
		
		String expectedPath = "foo" + File.separator + "bar";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleThreePartPathWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.append("foo").append("bar").append("hello");
		
		String expectedPath = "foo" + File.separator + "bar" 
							+ File.separator + "hello";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleThreePartPathWithInitTest() {
		PathBuilder builder = new PathBuilder("foo");
		builder.append("bar").append("hello");
		
		String expectedPath = "foo" + File.separator + "bar" 
							+ File.separator + "hello";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleFourPartPathWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.append("foo").append("bar").append("hello").append("world");
		
		String expectedPath = "foo" + File.separator + "bar" 
							+ File.separator + "hello"
							+ File.separator + "world";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleFourPartPathWithInitTest() {
		PathBuilder builder = new PathBuilder("foo");
		builder.append("bar").append("hello").append("world");
		
		String expectedPath = "foo" + File.separator + "bar" 
							+ File.separator + "hello"
							+ File.separator + "world";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleTwoPartPathPrependWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.prepend("bar").prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleTwoPartPathPrependWithInitTest() {
		PathBuilder builder = new PathBuilder("bar");
		builder.prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleThreePartPathPrependWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.prepend("hello").prepend("bar").prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar" 
				+ File.separator + "hello";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleThreePartPathPrependWithInitTest() {
		PathBuilder builder = new PathBuilder("hello");
		builder.prepend("bar").prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar" 
				+ File.separator + "hello";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleThreePartPathPrependAppendWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.append("bar").append("hello").prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar" 
				+ File.separator + "hello";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleThreePartPathPrependAppendWithInitTest() {
		PathBuilder builder = new PathBuilder("bar");
		builder.append("hello").prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar" 
				+ File.separator + "hello";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleFourPartPathPrependAppendWithoutInitTest() {
		PathBuilder builder = new PathBuilder();
		builder.append("hello").prepend("bar").append("world").prepend("foo");
		
		String expectedPath = "foo" + File.separator + "bar" 
							+ File.separator + "hello"
							+ File.separator + "world";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
	
	@Test
	public void simpleFourPartPathPrependAppendWithInitTest() {
		PathBuilder builder = new PathBuilder("hello");
		builder.prepend("bar").append("world").prepend("foo");

		
		String expectedPath = "foo" + File.separator + "bar" 
							+ File.separator + "hello"
							+ File.separator + "world";
		
		Assert.assertEquals(expectedPath, builder.toString());
	}
}
