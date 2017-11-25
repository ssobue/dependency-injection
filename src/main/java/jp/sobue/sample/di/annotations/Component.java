package jp.sobue.sample.di.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Component Annotation: regist dependency injection container automatically, when scanned annotated classes
 *
 * @author Sho Sobue
 * @see java.lang.annotation.Annotation
 */
@Retention(RUNTIME)
@Target({ElementType.TYPE})
public @interface Component {

}
