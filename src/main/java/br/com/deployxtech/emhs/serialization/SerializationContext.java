/**
 * 
 */
package br.com.deployxtech.emhs.serialization;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import br.com.deployxtech.emhs.serialization.impl.GsonSerializer;
import br.com.deployxtech.emhs.serialization.impl.ObjectStreamSerializer;

/**
 * @author Francisco Silva
 *
 */
public class SerializationContext {

	public final static String NATIVE = "NATIVE";
	public final static String REST = "REST";
	public final static String DEFAULT = REST;

	private static Map<String, SerializationContext> contexts = new HashMap<>();
	private Serializer serializer;
	
	static {
		SerializationContext.register(REST, new GsonSerializer());
		SerializationContext.register(NATIVE, new ObjectStreamSerializer());
	}

	private SerializationContext(Serializer serializer) {
		this.serializer = serializer;
	}

	public String serializar(Object object) {
		return this.serializer.serialize(object);
	}

	public <T> T unserialize(String object, Type clazz) {
		return this.serializer.unserialize(object, clazz);
	}

	public static void register(String name, Serializer serializer) {
		contexts.put(name, new SerializationContext(serializer));
	}

	public static SerializationContext get(String name) {
		return contexts.get(name);
	}

	public static SerializationContext getDefault() {
		return SerializationContext.get(DEFAULT);
	}
}