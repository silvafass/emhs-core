package br.com.deployxtech.emhs.serialization.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.Base64;

import br.com.deployxtech.emhs.serialization.Serializer;

/**
 * @author francisco.silva
 *
 */
public class ObjectStreamSerializer implements Serializer {

	public String serialize(Object object) {
		String result = null;
		ByteArrayOutputStream byteOutput;
		try (ObjectOutputStream output = new ObjectOutputStream(
				(byteOutput = new ByteArrayOutputStream()))) {
			output.writeObject(object);
			result = Base64.getEncoder().encodeToString(byteOutput.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	public <T> T unserialize(String object, Type type) {
		Object result = null;
		byte[] bytes = null;
		bytes = Base64.getDecoder().decode(object);
		try (ObjectInputStream input = new ObjectInputStream(
				new ByteArrayInputStream(bytes))) {
			result = input.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T)result;
	}
}