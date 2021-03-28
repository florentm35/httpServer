package fr.florent.httpserver.util;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ResourceHelper {


    public static Set<Class<?>> getTypesAnnotatedWith(String packageScan, Class<? extends Annotation> annotationCLass) {
        Reflections reflections = new Reflections(packageScan, new TypeAnnotationsScanner(), new SubTypesScanner());
        return reflections.getTypesAnnotatedWith(annotationCLass);

    }


    /**
     * Get method from tClass who is annoted by annotation
     *
     * @param tClass
     * @param annotation
     * @return
     */
    public static Set<Method> getAnnotedMethodFromClass(Class tClass, Class annotation) {
        Set<Method> methodList = new HashSet<>();

        for (Method method : tClass.getMethods()) {
            if (method.getAnnotation(annotation) != null) {
                methodList.add(method);
            }
        }

        return methodList;
    }

}
