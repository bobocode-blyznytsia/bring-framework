package com.bringframework;

import com.bringframework.context.AnnotationConfigApplicationContext;
import com.bringframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Entry point to work with Bring Application.
 */
@Slf4j
public class BringApplication {

  private ApplicationContext applicationContext;

  public BringApplication() {
  }

  /**
   * Runs Bring Application and creates {@code ApplicationContext}.
   */
  public void run() {
    log.info("Starting Bring application...");
    applicationContext = createApplicationContext();
  }

  private ApplicationContext createApplicationContext() {
    return new AnnotationConfigApplicationContext();
  }

  /**
   * Returns created before application context.
   *
   * @return created application context
   */
  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }

}
