package com.bringframework;

import com.bringframework.annotations.Autowired;
import com.bringframework.annotations.Component;
import com.bringframework.annotations.Qualifier;

//TODO Remove. Added for demonstration purpose
@Component
public class ComponentSomeClass {

  @Autowired()
  @Qualifier("someClass1")
  private SomeClass someClass1;

  @Autowired()
  @Qualifier("someClass2")
  private SomeClass someClass2;
  public void someMethod() {
    someClass1.someMethod();
    someClass2.someMethod();
  }
}
