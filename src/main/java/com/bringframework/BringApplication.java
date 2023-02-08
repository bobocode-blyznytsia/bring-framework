package com.bringframework;

import static com.bringframework.util.BeanUtils.validatePackageName;

import com.bringframework.context.AnnotationConfigApplicationContext;
import com.bringframework.context.ApplicationContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Entry point to work with Bring Application.
 */
@Slf4j
public class BringApplication {

  private final String packageName;
  private ApplicationContext applicationContext;

  public BringApplication(String packageName) {
    validatePackageName(packageName);
    this.packageName = packageName;
  }

  /**
   * Runs Bring Application and creates {@link ApplicationContext}.
   *
   * @return created application context
   */
  public ApplicationContext run() {
    log.info("Starting Bring application...");
    this.applicationContext = new AnnotationConfigApplicationContext(packageName);
    log.info("Bring application was started successfully.");
    return applicationContext;
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
