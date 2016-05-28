/**
 * 
 */
package br.com.deployxtech.emhs.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

/**
 * @author Francisco Silva
 *
 */
public class SearchUtils {

	public static List<Class<?>> scan() {
		List<Class<?>> types = new ArrayList<>();

		FastClasspathScanner scanner = new FastClasspathScanner(
				EmhsConfiguraction.getInstance().getScanPackage());
		if (EmhsConfiguraction.getInstance().getScanClass() != null) {
			for (Class<?> type: EmhsConfiguraction.getInstance().getScanClass()) {
				if (type.isInterface()) {
					scanner.matchClassesImplementing(type, c -> types.add(c));
				}
				else {
					scanner.matchSubclassesOf(type, c -> types.add(c));
				}
			}	
		}
		if (EmhsConfiguraction.getInstance().getScanAnnotation() != null) {
			for (Class<? extends Annotation> annotationClass: EmhsConfiguraction.getInstance().getScanAnnotation()) {
				scanner.matchClassesWithAnnotation(annotationClass, c -> types.add(c));
			}
		}
		scanner.scan();

		return types;
	}
}
