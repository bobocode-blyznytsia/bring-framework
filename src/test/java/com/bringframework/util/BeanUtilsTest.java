package com.bringframework.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.bringframework.exceptions.BeanInitializationException;
import java.util.AbstractList;
import java.util.List;
import org.junit.jupiter.api.Test;

class BeanUtilsTest {

  @Test
  void shouldThrowBeanInitializationExceptionWhenNoDefaultConstructorFound() {
    BeanInitializationException actualException =
        assertThrows(BeanInitializationException.class, () -> BeanUtils.createInstance(Long.class));
    assertEquals(actualException.getCause().getClass(), NoSuchMethodException.class);
  }

  @Test
  void shouldThrowBeanInitializationExceptionWhenNoDefaultPublicConstructorFound() {
    BeanInitializationException actualException =
        assertThrows(BeanInitializationException.class, () -> BeanUtils.createInstance(Math.class));
    assertEquals(actualException.getCause().getClass(), NoSuchMethodException.class);
  }

  @Test
  void shouldThrowBeanInitializationExceptionWhenProvidedBeanTypeIsAnInterface() {
    BeanInitializationException actualException =
        assertThrows(BeanInitializationException.class, () -> BeanUtils.createInstance(List.class));
    assertEquals(actualException.getCause().getClass(), NoSuchMethodException.class);
  }

  @Test
  void shouldThrowBeanInitializationExceptionWhenProvidedBeanTypeIsAnAbstractClass() {
    BeanInitializationException actualException = assertThrows(BeanInitializationException.class,
        () -> BeanUtils.createInstance(AbstractList.class));
    assertEquals(actualException.getCause().getClass(), NoSuchMethodException.class);
  }

}
