package com.auto.mark.utils;

import org.junit.Assert;
import org.junit.Test;

public class UrlParameterTest {
	@Test
	public void simpleParamTest() {
		UrlParameter param = new UrlParameter("foo", "bar");
		
		Assert.assertEquals("foo", param.getParameterName());
		Assert.assertEquals("bar", param.getParameterValue());
		Assert.assertEquals("foo=bar", param.toString());
	}
}
