package com.bringframework;

import com.bringframework.annotation.Autowired;
import com.bringframework.annotation.Component;
import com.bringframework.annotation.Qualifier;

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
