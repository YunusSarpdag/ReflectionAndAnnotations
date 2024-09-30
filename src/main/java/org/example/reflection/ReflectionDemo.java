package org.example.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class MyClass {
  private MyClass() {
    System.out.println("Constructor called");
  }
}

public class ReflectionDemo {
  public static void main(String[] args) throws Exception {
    Class cls = Class.forName("org.example.reflection.MyClass");
    Constructor constructor = cls.getDeclaredConstructor();
    constructor.setAccessible(true);
    MyClass myClass = (MyClass) constructor.newInstance();
    Class cls2 = Class.forName("org.example.reflection.MyClass");
    // forname points to the same class object in heap memory
    System.out.println("-------is same class----------");
    System.out.println(cls == cls2);
    Class<?> cls3 = MyClass.class;
    Constructor constructor2 = cls.getDeclaredConstructor();
    constructor2.setAccessible(true);
    MyClass myClass2 = (MyClass) constructor.newInstance();
    Class<?> cls4 = MyClass.class;
    System.out.println("-------is same class----------");
    System.out.println(cls3 == cls4);


    //--------------fields
    Entity entity = new Entity(1, "John");
    Class entityClass = entity.getClass();
    Field[] fields = entityClass.getFields();
    System.out.println("-------public Fields----------");
    for(Field field : fields) {
      System.out.println(field.getName());
    }
    System.out.println("------public & private Fields------------");
    Field[] declaredFields = entityClass.getDeclaredFields();
    for(Field field : declaredFields) {
      System.out.println(field.getName());
    }

    System.out.println("------public field-----------");
    Field field = entityClass.getField("name");
    field.set(entity, "John Doe");
    System.out.println(entity.getName());


    System.out.println("-------private & private Field----------");
    Field field2 = entityClass.getDeclaredField("id");
    field2.setAccessible(true);
    field2.set(entity,2);
    System.out.println(entity.getId());

    //-----------------methods
    System.out.println("-------methods----------");
    Method[] methods = entityClass.getMethods();
    for(Method method : methods) {
      System.out.println(method.getName());
    }

    System.out.println("-------getDeclaredMethods----------");
    Method[] declaredMethods = entityClass.getDeclaredMethods();
    for (Method method : declaredMethods) {
      System.out.println(method.getName());
    }

    System.out.println("-------public method invocation----------");
    Method publicMethod = entityClass.getDeclaredMethod("getName");;
    System.out.println(publicMethod.invoke(entity));

    System.out.println("-------private method invocation----------");
    Method privateMethod = entityClass.getDeclaredMethod("privateMethod");
    privateMethod.setAccessible(true);
    privateMethod.invoke(entity);

    System.out.println("-------public Constructor----------");
    Constructor[] constructors = entityClass.getConstructors();
    for (Constructor constructor1 : constructors) {
      System.out.println(constructor1);
    }

    System.out.println("-------public - private Constructor----------");
    Constructor[] declaredConstructors = entityClass.getDeclaredConstructors();
    for (Constructor constructor1 : declaredConstructors) {
      System.out.println(constructor1);
    }

    System.out.println("-------public getConstructor----------");
    Constructor<?> publicConstructor = entityClass.getConstructor(int.class, String.class);
    Entity entity1 = (Entity) publicConstructor.newInstance(3, "Jane");
    System.out.println(entity1.getId());

    System.out.println("-------private getDeclaredConstructor----------");
    Constructor<Entity> privateConstructor = entityClass.getDeclaredConstructor();
    privateConstructor.setAccessible(true);
    Entity entity2 = privateConstructor.newInstance();
    System.out.println(entity2.getId());

    System.out.println("-------Modifier----------");
    int modifiers = entityClass.getModifiers();
    System.out.println(Modifier.isPublic(modifiers)+" : " + Modifier.toString(modifiers));
    int modifier2 = privateConstructor.getModifiers();
    System.out.println(Modifier.isPublic(modifier2)+" : " + Modifier.toString(modifier2));


  }
}
