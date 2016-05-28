/**
 * 
 */
package br.com.deployxtech.emhs.router;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import br.com.deployxtech.emhs.core.annotation.Path;
import br.com.deployxtech.emhs.core.annotation.Service;
import br.com.deployxtech.emhs.service.ServiceRequest;
import br.com.deployxtech.emhs.service.ServiceResponse;
import br.com.deployxtech.emhs.utils.EmhsConfiguraction;

/**
 * @author Francisco Silva
 *
 */
public class RouterControl {

	private Map<String, RouterProvider> providers;

	public RouterControl() {
		providers = new HashMap<>();
	}

	public void createProvider(Class<?> c) {
		for (Method method: c.getMethods()) {
			if (!Modifier.isStatic(method.getModifiers())
					&& isOfScan(method.getDeclaringClass())) {
				String url = "";
				if (method.isAnnotationPresent(Path.class)) {
					url = method.getAnnotation(Path.class).value();
				}
				else if (c.isAnnotationPresent(Service.class)
						&& !c.getAnnotation(Service.class).value().isEmpty()) {
					url+=c.getAnnotation(Service.class).value();
					url+="/"+method.getName();
				}
				else {
					url+="/"+c.getSimpleName();
					url+="/"+method.getName();
				}
				
				url = url.toLowerCase();
				
				RouterProvider provider = providers.get(url);
				if (provider == null) {
					provider = new RouterProvider();
					providers.put(url, provider);
				}
				provider.createServiceRouter(method, c);
			}
		}
	}

	public void invoke(ServiceRequest request, ServiceResponse response) throws ReflectiveOperationException, IOException {
		RouterProvider provider = providers.get(request.getURL());
		if (provider != null) {
			response.getWriter().write(provider.invoke(request, response));
		}
		else {
			response.end(404);
		}
	}

	private boolean isOfScan(Class<?> c) {
		if (EmhsConfiguraction.getInstance().getScanClass() != null) {
			for (Class<?> type: EmhsConfiguraction.getInstance().getScanClass()) {
				if (type.isAssignableFrom(c)) {
					return true;
				}
			}
		}
		if (EmhsConfiguraction.getInstance().getScanAnnotation() != null) {
			for (Class<? extends Annotation> type: EmhsConfiguraction.getInstance().getScanAnnotation()) {
				if (c.isAnnotationPresent(type)) {
					return true;
				}
			}
		}
		return false;
	}
}
