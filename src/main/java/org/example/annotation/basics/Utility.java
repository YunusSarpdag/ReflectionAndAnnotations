package org.example.annotation.basics;

@MostUsed
public class Utility {

  @MostUsed("Addition is Most Used")
  int add(int first, int second) {
    return first + second;
  }

  int add(int first, int second, int third) {
    return first + second + third;
  }
}

class SubUtility extends Utility {
}
