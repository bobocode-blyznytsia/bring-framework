package com.bringframework.util;

import com.bringframework.exceptions.BeanInitializationException;

/**
 * Utility class for creating bean instance using reflection API.
 */
public final class BeanUtils {

  private BeanUtils() {
  }

  public static <T> T createInstance(Class<T> beanType) {
    try {
      return beanType.getConstructor().newInstance();
    } catch (Throwable e) {
      throw new BeanInitializationException(beanType, e);
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
