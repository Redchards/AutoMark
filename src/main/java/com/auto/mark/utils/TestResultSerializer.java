package com.auto.mark.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.auto.mark.runner.TestResult;

public class TestResultSerializer {
	public static String serialize(TestResult results) throws TestResultSerializationException {
		String serializedObject = "";
		
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
		    ObjectOutputStream so = new ObjectOutputStream(bo);
		    so.writeObject(results);
		    so.flush();
		    serializedObject = new String(Base64.encodeBase64(bo.toByteArray()));
		    so.close();
		    bo.close();
		} catch (IOException e) {
			throw new TestResultSerializationException("serialization.", e);
		}
		
		return serializedObject;
	}
	
	public static TestResult deserialize(String serialized) throws TestResultSerializationException {
		TestResult results;

		if(serialized == null || "".equals(serialized)) {
			throw new TestResultSerializationException("deserialization, serialized data is invalide (empty).");
		}
		
		try {
			byte b[] = Base64.decodeBase64(serialized.getBytes()); 
		    ByteArrayInputStream bi = new ByteArrayInputStream(b);
		    ObjectInputStream si = new ObjectInputStream(bi);
		    results = (TestResult) si.readObject();
		    si.close();
		    bi.close();
		} catch (IOException e) {
			throw new TestResultSerializationException("deserialization.", e);
		} catch (ClassNotFoundException e) {
			throw new TestResultSerializationException("deserialization.", e);
		}
		
		return results;
	}
}
