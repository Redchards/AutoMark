package com.auto.mark.utils;

import org.junit.Assert;
import org.junit.Test;

public class CharSequenceHelperTest {
	
	@Test
	public void getFirstCharTest1() {
		Assert.assertEquals('h', CharSequenceHelper.getFirstOf("hello"));
	}
	
	@Test
	public void getFirstCharTest2() {
		Assert.assertEquals('T', CharSequenceHelper.getFirstOf("This is a simple char sequence used to test our method"));
	}
	
	@Test
	public void getFirstCharTest3() {
		Assert.assertEquals('f', CharSequenceHelper.getFirstOf("foobar"));
	}
	
	@Test
	public void getLastCharTest1() {
		Assert.assertEquals('o', CharSequenceHelper.getLastOf("hello"));
	}
	
	@Test
	public void getLastCharTest2() {
		Assert.assertEquals('d', CharSequenceHelper.getLastOf("This is a simple char sequence used to test our method"));
	}
	
	@Test
	public void getLastCharTest3() {
		Assert.assertEquals('r', CharSequenceHelper.getLastOf("foobar"));
	}
	
	@Test
	public void getFirstCharAsStrTest1() {
		Assert.assertEquals("h", CharSequenceHelper.getFirstAsStrOf("hello"));
	}
	
	@Test
	public void getFirstCharAsStrTest2() {
		Assert.assertEquals("T", CharSequenceHelper.getFirstAsStrOf("This is a simple char sequence used to test our method"));
	}
	
	@Test
	public void getFirstCharAsStrTest3() {
		Assert.assertEquals("f", CharSequenceHelper.getFirstAsStrOf("foobar"));
	}
	
	@Test
	public void getLastCharAsStrTest1() {
		Assert.assertEquals("o", CharSequenceHelper.getLastAsStrOf("hello"));
	}
	
	@Test
	public void getLastCharAsStrTest2() {
		Assert.assertEquals("d", CharSequenceHelper.getLastAsStrOf("This is a simple char sequence used to test our method"));
	}
	
	@Test
	public void getLastCharAsStrTest3() {
		Assert.assertEquals("r", CharSequenceHelper.getLastAsStrOf("foobar"));
	}
}
