package org.example.annotation.spring;

import org.example.annotation.spring.annotations.Autowired;
import org.example.annotation.spring.annotations.Component;
import org.example.annotation.spring.annotations.ComponentScan;
import org.example.annotation.spring.annotations.Configuration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

public class ApplicationContext {
  private static Map<Class<?>, Object> contex = new HashMap<>();

  public ApplicationContext(Class<AppConfig> appConfigClass) {
    Spring.initialazeSpringContext(appConfigClass);
  }

  public <T> T getBean(Class<T> clss) {
      T object = (T) contex.get(clss);
      Field[] fields = clss.getDeclaredFields();
      injectBeans(object, fields);
      return object;
  }

  private <T> void injectBeans(T object, Field[] fields) {
    for (Field field : fields) {
      if (field.isAnnotationPresent(Autowired.class)) {
        field.setAccessible(true);
        Class<?> type = field.getType();
        Object innerObject = contex.get(type);
        try {
          field.set(object, innerObject);
          Field[] declaredFields = innerObject.getClass().getDeclaredFields();
          injectBeans(innerObject, declaredFields);
        } catch (Exception e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  private static class Spring {
    public static void initialazeSpringContext(Class<?> clss) {
      if (clss.isAnnotationPresent(Configuration.class)) {
        ComponentScan annotation = clss.getAnnotation(ComponentScan.class);
        String pck = annotation.value();
        // bin folder
        String packageStr = "C:/Test/ReflectionAndAnnotations/target/classes/" + pck.replace(".", "/");
        File[] files = findClasses(new File(packageStr));
        for (File file : files) {
          String name = pck + "." + file.getName().replace(".class", "");
          try {
            Class<?> loadingClass = Class.forName(name);
            if (loadingClass.isAnnotationPresent(Component.class)) {
              Constructor<?> constructor = loadingClass.getConstructor();
              constructor.setAccessible(true);
              Object object = constructor.newInstance();
              contex.put(loadingClass, object);
            }
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }
      } else {
        throw new RuntimeException("Class is not annotated with @Configuration");
      }
    }

    private static File[] findClasses(File file) {
      if (!file.exists()) {
        throw new RuntimeException("File not found");
      } else {
        return file.listFiles(e -> e.getName().endsWith(".class"));
      }
    }
  }
}
