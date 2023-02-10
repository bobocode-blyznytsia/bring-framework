package com.bringframework.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bringframework.registry.BeanDefinition;
import com.bringframework.registry.BeanDefinitionRegistry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DefaultBeanDefinitionScannerTest {

  @Mock
  private BeanDefinitionRegistry registry;

  @Captor
  private ArgumentCaptor<String> beanDefinitionNameArgumentCaptor;

  @Captor
  private ArgumentCaptor<BeanDefinition> beanDefinitionArgumentCaptor;

  @InjectMocks
  private DefaultBeanDefinitionScanner underTest;

  @Test
  void shouldRegisterBeans() {
    underTest.registerBeans("com.bringframework");

    verify(registry, times(1)).registerBeanDefinition(beanDefinitionNameArgumentCaptor.capture(),
        beanDefinitionArgumentCaptor.capture());

    var autowiredFields = beanDefinitionArgumentCaptor.getValue().getAutowiredFieldsMetadata();
    assertEquals("testComponent", beanDefinitionNameArgumentCaptor.getValue());
    assertEquals(1, autowiredFields.size());
    assertTrue(autowiredFields.containsKey("testService"));
  }

}
