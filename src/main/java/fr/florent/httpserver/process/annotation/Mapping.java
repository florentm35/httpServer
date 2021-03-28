package fr.florent.httpserver.process.annotation;

import fr.florent.httpserver.http.HttpMethod;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Mapping {

    String path() default "";

    HttpMethod method() default HttpMethod.GET;


}
