package com.bringframework;

import com.bringframework.context.AnnotationConfigApplicationContext;
import com.bringframework.context.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BringApplication {

  private static final Logger LOGGER = LoggerFactory.getLogger(BringApplication.class);
  private ApplicationContext applicationContext;

  public BringApplication() {
  }

  public void run() {
    LOGGER.info("Starting Bring application...");
    applicationContext = createApplicationContext();
  }

  private ApplicationContext createApplicationContext() {
    return new AnnotationConfigApplicationContext();
  }

  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
