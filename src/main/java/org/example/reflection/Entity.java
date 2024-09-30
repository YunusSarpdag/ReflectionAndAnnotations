package org.example.reflection;

public class Entity {
  private int id;
  public String name;

  public Entity(int id, String name) {
    this.id = id;
    this.name = name;
  }

  private Entity() {
    this(0, "");
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private void privateMethod() {
    System.out.println("Private method called");
  }
}
