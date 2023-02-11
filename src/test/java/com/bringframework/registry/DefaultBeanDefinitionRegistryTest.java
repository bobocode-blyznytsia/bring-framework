package com.bringframework.registry;

import com.bringframework.exception.BeanDefinitionDuplicateNameException;
import java.lang.reflect.Method;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultBeanDefinitionRegistryTest {
  private DefaultBeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();
  private BeanDefinition beanDefinition = new BeanDefinition(Object.class, Map.of());
  private Method factoryMethod = Object.class.getMethods()[0];
  private ConfigBeanDefinition configBeanDefinition = new ConfigBeanDefinition(factoryMethod);

  @Test
  void testRegisterBeanDefinition() {
    beanDefinitionRegistry.registerBeanDefinition("bean1", beanDefinition);
    Assertions.assertEquals(beanDefinition, beanDefinitionRegistry.getBeanDefinition("bean1"));
  }

  @Test
  void testDuplicateNameException() {
    beanDefinitionRegistry.registerBeanDefinition("bean2", beanDefinition);
    Assertions.assertThrows(BeanDefinitionDuplicateNameException.class,
        () -> beanDefinitionRegistry.registerBeanDefinition("bean2", beanDefinition));
  }

  @Test
  void testGetAllBeanDefinitions() {
    beanDefinitionRegistry.registerBeanDefinition("bean3", beanDefinition);
    beanDefinitionRegistry.registerBeanDefinition("bean4", beanDefinition);
    Assertions.assertEquals(2, beanDefinitionRegistry.getAllBeanDefinitions().size());
  }

  @Test
  void testRegisterConfigBeanDefinition() {
    beanDefinitionRegistry.registerConfigBeanDefinition("configBean1", configBeanDefinition);
    Assertions.assertEquals(configBeanDefinition, beanDefinitionRegistry.getConfigBeanDefinition("configBean1"));
  }

  @Test
  void testConfigDuplicateNameException() {
    beanDefinitionRegistry.registerConfigBeanDefinition("configBean2", configBeanDefinition);
    Assertions.assertThrows(BeanDefinitionDuplicateNameException.class,
        () -> beanDefinitionRegistry.registerConfigBeanDefinition("configBean2", configBeanDefinition));
  }

  @Test
  void testGetAllConfigBeanDefinitions() {
    beanDefinitionRegistry.registerConfigBeanDefinition("configBean3", configBeanDefinition);
    beanDefinitionRegistry.registerConfigBeanDefinition("configBean4", configBeanDefinition);
    Assertions.assertEquals(2, beanDefinitionRegistry.getAllConfigBeanDefinitions().size());
  }

  @Test
  void testBeanAndConfigBeanDuplicateNameException() {
    beanDefinitionRegistry.registerBeanDefinition("bean5", beanDefinition);
    Assertions.assertThrows(BeanDefinitionDuplicateNameException.class,
        () -> beanDefinitionRegistry.registerConfigBeanDefinition("bean5", configBeanDefinition));
  }

}
