package com.bringframework.util;

import com.bringframework.exception.BeanInitializationException;
import java.lang.reflect.Method;
import lombok.experimental.UtilityClass;

/**
 * BeanUtils is a utility class that provides methods for creating instances of beans using reflection API. This class
 * makes it possible to instantiate beans without using traditional constructors, but instead using factory methods and
 * providing the required parameters.
 * <p>It offers two main methods:</p>
 * <ul>
 * <li>{@link #createInstance(Class)} - creates an instance of the given bean type using its default constructor.</li>
 * <li>{@link #createInstance(Method, Object...)} - creates an instance of the given bean type using the provided
 * factory method and its parameters.</li>
 * </ul>
 * <p>In addition, this class also provides a method for validating the package name that will be scanned for beans,
 * to ensure that the correct package name is specified.</p>
 */
@UtilityClass
public final class BeanUtils {

  /**
   * Creates an instance of the given bean type using its default constructor.
   *
   * @param beanType The class of the bean to be created.
   * @param <T>      The type of the bean to be created.
   * @return The created instance of the given bean type.
   * @throws BeanInitializationException If an exception occurs during the instance creation.
   */
  public static <T> T createInstance(Class<T> beanType) {
    try {
      return beanType.getConstructor().newInstance();
    } catch (Throwable e) {
      throw new BeanInitializationException(beanType, e);
    }
  }

  /**
   * Creates an instance of the given bean type using the provided factory method and its parameters.
   *
   * @param factoryMethod       The factory method to be used to create the instance.
   * @param parameterCandidates The parameters to be passed to the factory method.
   * @param <T>                 The type of the bean to be created.
   * @return The created instance of the given bean type.
   * @throws BeanInitializationException If an exception occurs during the instance creation.
   */
  public static <T> T createInstance(Method factoryMethod, Object... parameterCandidates) {
    try {
      factoryMethod.setAccessible(true);
      Class<?> configClass = factoryMethod.getDeclaringClass();
      Object configClassInstance = createInstance(configClass);
      return (T) factoryMethod.invoke(configClassInstance, parameterCandidates);
    } catch (Throwable e) {
      throw new BeanInitializationException(factoryMethod.getReturnType(), e);
    }
  }

  /**
   * Validate package which will be scanned and beans will be searched.
   *
   * @param packageName the package with beans
   */
  public static void validatePackageName(String packageName) {
    if (packageName == null || packageName.isBlank()) {
      throw new IllegalArgumentException(
          "The package name can't be empty or null. Please specify the correct package name.");
    }
  }

}
