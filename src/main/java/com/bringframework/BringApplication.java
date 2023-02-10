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

  //TODO Remove. Added for demonstration purpose
  public static void main(String[] args) {
    BringApplication bringApplication = new BringApplication("com.bringframework");
    bringApplication.run();
    ApplicationContext applicationContext1 = bringApplication.getApplicationContext();

    System.out.println(String.format("%1$20s%2$30s", "BeanName", "SimpleBeanClass"));
    System.out.println("==================================================");
    applicationContext1.getAllBeans().entrySet().forEach(entry -> System.out.println(
        String.format("%1$20s%2$30s", entry.getKey(), entry.getValue().getClass().getSimpleName())));

    System.out.println();
    System.out.println("==================================================");
    ComponentClass2 bean = applicationContext1.getBean(ComponentClass2.class);
    bean.someMethod();

    System.out.println();
    System.out.println("==================================================");
    SomeClass2 someClass2 = applicationContext1.getBean(SomeClass2.class);
    someClass2.someMethod();

    System.out.println();
    System.out.println("==================================================");
    ComponentSomeClass componentSomeClass = applicationContext1.getBean(ComponentSomeClass.class);
    componentSomeClass.someMethod();
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
