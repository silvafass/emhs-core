package br.com.deployxtech.emhs.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * @author Francisco Silva
 *
 */
public interface ServiceResponse {

	void setCharacterEncoding(String value);

	void setContentType(String value);

	Map<String, List<String>> getHeaders();

	void addHeader(String key, String value);

	void sendResponseHeaders(int responseCode, int responseLength) throws IOException;

	StringWriter getWriter();

	ByteArrayOutputStream getOutputStream();

	void error(Exception e) throws IOException;

	void end(int responseCode) throws IOException;

	void end() throws IOException;

}