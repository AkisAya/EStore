package me.aki.estore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Aki on 2017/2/13.
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OpenTransaction {
}
