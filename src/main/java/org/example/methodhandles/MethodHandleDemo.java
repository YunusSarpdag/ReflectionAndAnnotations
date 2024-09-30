package org.example.methodhandles;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;

public class MethodHandleDemo {
  public static void main(String[] args) throws Throwable {
    Lookup lookup = MethodHandles.lookup();
    Class<?> clss = Class.forName("org.example.methodhandles.Student");
    MethodType methodType = MethodType.methodType(String.class);
    MethodHandle getCourseHandle = lookup.findVirtual(clss, "getCourse", methodType);
    Student s = new Student();
    s.setCourse("Java");
    System.out.println(getCourseHandle.invoke(s));
    MethodHandle constr = lookup.findConstructor(clss, MethodType.methodType(void.class));
    Student s2 = (Student)constr.invoke();
    System.out.println(s2);
    MethodHandle staticMethod = lookup.findStatic(clss, "getNumOfStudents", MethodType.methodType(int.class));
    System.out.println(staticMethod.invoke());

    //Lookup privateLookUp MethodHandles.privateLookupIn(clss, lookup);
    //MethodHandle getter = privateLookUp.findGetter(clss, "name", String.class);
    //System.out.println(getter.invoke(s));
  }
}
