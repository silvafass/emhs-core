package br.com.deployxtech.emhs.serialization;

import java.lang.reflect.Type;

/**
 * 
 * @author Francisco Silva
 *
 */
public interface Serializer {

	String serialize(Object object);
	<T> T unserialize(String object, Type clazz);
	
}