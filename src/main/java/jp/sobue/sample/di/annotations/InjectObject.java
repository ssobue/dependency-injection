package jp.sobue.sample.di.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * InjectObject Annotation: for dependency injection field annotation.
 *
 * @author Sho Sobue
 * @see java.lang.annotation.Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface InjectObject {}
