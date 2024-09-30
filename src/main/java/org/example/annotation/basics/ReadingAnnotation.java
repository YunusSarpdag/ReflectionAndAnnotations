package org.example.annotation.basics;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class ReadingAnnotation {
  public static void main(String[] args) throws Exception {
    Class<?> clss = Class.forName("org.example.annotation.basics.Utility");
    Constructor<?> constructor = clss.getConstructor();
    Utility utility = (Utility)constructor.newInstance();

    Method[] methods = clss.getDeclaredMethods();
    for (Method method : methods) {
      if(method.isAnnotationPresent(MostUsed.class)){
        int result = (int)method.invoke(utility, 10, 20);
        MostUsed annotation = method.getAnnotation(MostUsed.class);
        System.out.println(annotation.value());
        System.out.println(result);
      }
    }
  }
}
