package br.com.deployxtech.emhs.serialization.impl;

import java.lang.reflect.Type;

import com.google.gson.Gson;

import br.com.deployxtech.emhs.serialization.Serializer;

/**
 * @author francisco.silva
 *
 */
public class GsonSerializer implements Serializer {

	private Gson gson;

	public GsonSerializer(Gson gson) {
		this.gson = gson;
	}

	public GsonSerializer() {
		this(new Gson());
	}

	public String serialize(Object object) {
		return gson.toJson(object);
	}

	public <T> T unserialize(String object, Type type) {
		return gson.fromJson((String)object, type);
	}
}