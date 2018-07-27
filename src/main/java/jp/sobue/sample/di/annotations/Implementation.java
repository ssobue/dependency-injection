package jp.sobue.sample.di.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Implementation Annotation: regist dependency injection container automatically, when scanned
 * annotated classes.
 *
 * @author Sho Sobue
 * @see java.lang.annotation.Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Implementation {}
