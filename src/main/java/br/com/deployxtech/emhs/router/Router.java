/**
 * 
 */
package br.com.deployxtech.emhs.router;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.deployxtech.emhs.serialization.SerializationContext;
import br.com.deployxtech.emhs.service.ServiceRequest;
import br.com.deployxtech.emhs.service.ServiceResponse;

/**
 * @author Francisco Silva
 *
 */
public class Router {
	
	private static boolean namePresent = false;

	private Method method;
	private Class<?> c;

	private List<String> parameterNames = null;
	private Parameter[] parameters = null;

	public Router(Method method, Class<?> c) {
		this.c = c;
		this.method = method;
		if (method.getParameters().length > 0) {
			parameterNames = new ArrayList<String>();
			parameters = method.getParameters();
			for (Parameter parameter: parameters) {
				parameterNames.add(parameter.getName());
				if (!namePresent && parameter.isNamePresent()) {
					namePresent = parameter.isNamePresent();
				}
			}
		}
	}

	public int checkParameters(Set<String> keys) {
		if (parameterNames != null) {
			int searched = 0;
			for (String parameterName: keys) {
				if (parameterNames.contains(parameterName)) {
					searched++;
				}
			}
			return searched;
		}
		else {
			return 0;
		}
	}

	public int getParameterCount() {
		return method.getParameterTypes().length;
	}

	public String invoke(ServiceRequest request, ServiceResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		String userAgent = (String)request.getAttribute("User-Agent");
		if (!SerializationContext.NATIVE.equals(userAgent)) {
			userAgent = SerializationContext.DEFAULT;
		}

		SerializationContext serializer = SerializationContext.get(userAgent);
		Object[] paramns = new Object[method.getParameterTypes().length];

		if (parameters != null && parameters.length > 0) {
			for (int index = 0; index < parameters.length; index++) {
				paramns[index] = serializer.unserialize(request.getParamns().get(parameterNames.get(index)), parameters[index].getType());
			}
		}

		return serializer.serializar(method.invoke(c.newInstance(), paramns));
	}
	
	public static boolean isParameterNamePresent() {
		return namePresent;
	}
}
