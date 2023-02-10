package com.bringframework;

//TODO Remove. Added for demonstration purpose
public class SomeClass2 {

  private SomeClass[] someClass;

  public SomeClass2(SomeClass... someClass) {
    this.someClass = someClass;
  }

  public void someMethod() {
    for (SomeClass some : someClass) {
      some.someMethod();
    }
  }
}
