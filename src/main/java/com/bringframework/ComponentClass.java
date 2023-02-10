package com.bringframework;

import com.bringframework.annotation.Autowired;
import com.bringframework.annotation.Component;
import java.util.Random;

//TODO Remove. Added for demonstration purpose
@Component
public class ComponentClass {


  @Autowired
  private Random random;


  public void someMethod() {
    System.out.println(random.nextDouble());
  }
}
