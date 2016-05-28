/**
 * 
 */
package br.com.deployxtech.emhs.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Francisco Silva
 *
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface Path {
	String value();
}
