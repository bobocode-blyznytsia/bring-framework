package com.bringframework;

import com.bringframework.annotations.Autowired;
import com.bringframework.annotations.Component;

//TODO Remove. Added for demonstration purpose
@Component
public class ComponentClass2 {


  @Autowired
  private ComponentClass componentClass;


  public void someMethod() {
    componentClass.someMethod();
  }
}
