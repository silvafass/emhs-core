/**
 * 
 */
package br.com.deployxtech.emhs.utils;

import java.lang.annotation.Annotation;

import br.com.deployxtech.emhs.core.annotation.Service;

/**
 * @author Francisco Silva
 *
 */
public class EmhsConfiguraction {

	private static EmhsConfiguraction instance;

	private String[] scanPackage;
	private Class<? extends Annotation>[] scanAnnotation;
	private Class<?>[] scanClass;

	private String[] pathStatics;

	private EmhsConfiguraction() {
		setScanPackage("");
		setScanAnnotation(Service.class);
	}

	public EmhsConfiguraction setScanPackage(String... scanPackage) {
		this.scanPackage = scanPackage;
		return this;
	}

	public EmhsConfiguraction setScanAnnotation(Class<? extends Annotation>... scanAnnotation) {
		this.scanAnnotation = scanAnnotation;
		return this;
	}

	public EmhsConfiguraction setScanClass(Class<?>... scanClass) {
		this.scanClass = scanClass;
		return this;
	}

	public String[] getScanPackage() {
		return scanPackage;
	}

	public Class<? extends Annotation>[] getScanAnnotation() {
		return scanAnnotation;
	}

	public Class<?>[] getScanClass() {
		return scanClass;
	}

	public static EmhsConfiguraction getInstance() {
		if (instance == null) {
			instance = new EmhsConfiguraction();
		}
		return instance;
	}

	public String[] getPathStatics() {
		return pathStatics;
	}

	public void setPathStatics(String... pathStatics) {
		this.pathStatics = pathStatics;
	}
}
