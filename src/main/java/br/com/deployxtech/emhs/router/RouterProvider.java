/**
 * 
 */
package br.com.deployxtech.emhs.router;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.deployxtech.emhs.service.ServiceRequest;
import br.com.deployxtech.emhs.service.ServiceResponse;

/**
 * @author Francisco Silva
 *
 */
public class RouterProvider {

	private List<Router> routers = new ArrayList<>();

	public void createServiceRouter(Method method, Class<?> c) {
		routers.add(new Router(method, c));
	}

	public String invoke(ServiceRequest request, ServiceResponse response) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		Set<String> keys = request.getParamns().keySet();
		Router routerExec = null;
		int priority = 0;
		for (Router router: routers) {
			int tPriority = router.checkParameters(keys);
			if (router.getParameterCount() > 0 && tPriority == router.getParameterCount()) {
				routerExec = router;
				break;
			}
			else if (tPriority > priority) {
				routerExec = router;
				priority = tPriority; 
			}
			else if(keys.isEmpty() && router.getParameterCount() == 0) {
				routerExec = router;
			}
		}
		if (routerExec != null) {
			return routerExec.invoke(request, response);
		}
		else {
			return routers.get(0).invoke(request, response);
		}
	}
}
