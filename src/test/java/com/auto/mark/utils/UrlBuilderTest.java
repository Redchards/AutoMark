package com.auto.mark.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class UrlBuilderTest {
	@Test
	public void noParameterEmptyConstructorTest() {
		UrlBuilder builder = new UrlBuilder();
		
		String baseUrl = "http://base_url_test";
		
		builder.setBaseUrl(baseUrl);
		
		Assert.assertEquals(baseUrl, builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
	}
	
	@Test
	public void noParameterBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		
		UrlBuilder builder = new UrlBuilder(baseUrl);

		
		Assert.assertEquals(baseUrl, builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
	}
	
	@Test
	public void oneParameterConstructorBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param = new UrlParameter("foo", "bar");
		
		UrlBuilder builder = new UrlBuilder(baseUrl, param);

		
		Assert.assertEquals(baseUrl + "?" + param.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param.toString(), builder.getParameter(0).toString());
	}
	
	@Test
	public void twoParameterConstructorBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		
		UrlBuilder builder = new UrlBuilder(baseUrl, param1, param2);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString() + "&" + param2.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
		Assert.assertEquals(param2.toString(), builder.getParameter(1).toString());
	}
	
	@Test
	public void oneParameterConstructorListBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		
		List<UrlParameter> params = new ArrayList<>();
		params.add(param1);
		
		UrlBuilder builder = new UrlBuilder(baseUrl, params);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
	}
	
	@Test
	public void twoParameterConstructorListBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		
		List<UrlParameter> params = new ArrayList<>();
		params.add(param1);
		params.add(param2);
		
		UrlBuilder builder = new UrlBuilder(baseUrl, params);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString() + "&" + param2.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
		Assert.assertEquals(param2.toString(), builder.getParameter(1).toString());
	}
	
	@Test
	public void threeParameterConstructorListBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		UrlParameter param3 = new UrlParameter("not", "everybody");
		
		List<UrlParameter> params = new ArrayList<>();
		params.add(param1);
		params.add(param2);
		params.add(param3);
		
		UrlBuilder builder = new UrlBuilder(baseUrl, params);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString() + "&" + param2.toString() + "&" + param3.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
		Assert.assertEquals(param2.toString(), builder.getParameter(1).toString());
		Assert.assertEquals(param3.toString(), builder.getParameter(2).toString());
	}
	
	@Test
	public void oneParameterMethodListBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		
		List<UrlParameter> params = new ArrayList<>();
		params.add(param1);
		
		UrlBuilder builder = new UrlBuilder(baseUrl);
		
		builder.addAll(params);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
	}
	
	@Test
	public void twoParameterMethodListBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		
		List<UrlParameter> params = new ArrayList<>();
		params.add(param1);
		params.add(param2);
		
		UrlBuilder builder = new UrlBuilder(baseUrl);
		
		builder.addAll(params);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString() + "&" + param2.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
		Assert.assertEquals(param2.toString(), builder.getParameter(1).toString());
	}
	
	@Test
	public void threeParameterMethodListBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		UrlParameter param3 = new UrlParameter("not", "everybody");
		
		List<UrlParameter> params = new ArrayList<>();
		params.add(param1);
		params.add(param2);
		params.add(param3);
		
		UrlBuilder builder = new UrlBuilder(baseUrl);
		
		builder.addAll(params);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString() + "&" + param2.toString() + "&" + param3.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
		Assert.assertEquals(param2.toString(), builder.getParameter(1).toString());
		Assert.assertEquals(param3.toString(), builder.getParameter(2).toString());
	}
	
	@Test
	public void threeParameterConstructorBaseUrlConstructorTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		UrlParameter param3 = new UrlParameter("not", "everybody");
		
		UrlBuilder builder = new UrlBuilder(baseUrl, param1, param2, param3);

		
		Assert.assertEquals(baseUrl + "?" + param1.toString() + "&" + param2.toString() + "&" + param3.toString(), builder.toString());
		Assert.assertEquals(baseUrl, builder.getBaseUrl());
		Assert.assertEquals(param1.toString(), builder.getParameter(0).toString());
		Assert.assertEquals(param2.toString(), builder.getParameter(1).toString());
		Assert.assertEquals(param3.toString(), builder.getParameter(2).toString());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void parameterOutOfBoundTest() {
		String baseUrl = "http://base_url_test";
		UrlParameter param1 = new UrlParameter("foo", "bar");
		UrlParameter param2 = new UrlParameter("hello", "world");
		UrlParameter param3 = new UrlParameter("not", "everybody");
		
		UrlBuilder builder = new UrlBuilder(baseUrl, param1, param2, param3);

		builder.getParameter(4);
	}
}
