package com.bringframework.registry;

import com.bringframework.exceptions.BeanDefinitionDuplicateNameException;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultBeanDefinitionRegistryTest {
  private DefaultBeanDefinitionRegistry beanDefinitionRegistry = new DefaultBeanDefinitionRegistry();
  private BeanDefinition beanDefinition = new BeanDefinitionImpl(Object.class, Map.of());

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
}
