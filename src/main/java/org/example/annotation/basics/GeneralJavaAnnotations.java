package org.example.annotation.basics;

import java.util.ArrayList;
import java.util.List;

class Parent{
  public void print(){
    System.out.println("Parent");
  }

 @Deprecated
  public void deprecated(){
    System.out.println("Deprecated");
  }
}

public class GeneralJavaAnnotations extends Parent{

  @Override
  public void print(){
    System.out.println("Child");
  }

  public static void main(String[] args) {
    @SuppressWarnings("unused")
    int a = 10;

    @SuppressWarnings({"unused", "rawtypes"})
    List list = new ArrayList();

    @SuppressWarnings("all")
    List list2 = new ArrayList();

    MyFunctionalInterface functionalInterface = () -> System.out.println("Functional Interface");
    functionalInterface.myMethod();
  }
}

@FunctionalInterface
interface MyFunctionalInterface{
  void myMethod();
}
