package jp.sobue.sample.di.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Autowired Annotation: for dependency injection field annotation
 *
 * @author Sho Sobue
 * @see java.lang.annotation.Annotation
 */
@Retention(RUNTIME)
@Target({ElementType.FIELD})
public @interface Autowired {

}
