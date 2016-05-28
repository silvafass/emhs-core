package br.com.deployxtech.emhs.service;

import java.util.Map;

/**
 * @author Francisco Silva
 *
 */
public interface ServiceRequest {

	String getParameter(String name);

	Map<String, String> getParamns();

	String getURL();

	Object getAttribute(String string);

}
