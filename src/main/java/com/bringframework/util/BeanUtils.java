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

}
