package com.bringframework.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import com.bringframework.exception.BeanInitializationException;
import java.util.AbstractList;
import java.util.List;
import lombok.NoArgsConstructor;
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

  @Test
  void shouldThrowExceptionWhenPackageNameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> BeanUtils.validatePackageName(null));
  }

  @Test
  void shouldThrowExceptionWhenPackageNameIsEmpty() {
    assertThrows(IllegalArgumentException.class, () -> BeanUtils.validatePackageName("   "));
  }

  @Test
  void shouldCreateInstanceWithNoArgsFactoryMethod() throws ReflectiveOperationException {
    var factoryMethod = TestConfiguration.class.getDeclaredMethod("factoryMethod");
    assertSame(TestConfiguration.MOCK_OBJECT, BeanUtils.createInstance(factoryMethod));
  }

  @Test
  void shouldCreateInstanceWithFactoryMethodWithArgs() throws ReflectiveOperationException {
    var factoryMethod = TestConfiguration.class.getDeclaredMethod("factoryMethodWithParam", Object.class);
    var mockParam = mock();
    assertSame(mockParam, BeanUtils.createInstance(factoryMethod, mockParam));
  }

  @Test
  void shouldWrapExceptionIfFactoryMethodFails() throws ReflectiveOperationException{
    var factoryMethod = TestConfiguration.class.getDeclaredMethod("failingFactoryMethod");
    assertThrows(BeanInitializationException.class,() -> BeanUtils.createInstance(factoryMethod));
  }
  @NoArgsConstructor
  static class TestConfiguration {

    private static final Object MOCK_OBJECT = new Object();

    Object factoryMethod() {
      return MOCK_OBJECT;
    }

    Object factoryMethodWithParam(Object obj) {
      return obj;
    }

    Object failingFactoryMethod(){
      throw new RuntimeException("It's not your day, mate. You've got an exception!");
    }

  }

}
