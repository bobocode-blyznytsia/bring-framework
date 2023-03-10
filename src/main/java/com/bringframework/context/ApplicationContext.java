package com.bringframework.context;

import java.util.Map;

/**
 * Interface for an Inversion of Control (IoC) container context. An IoC container is responsible
 * for instantiating, configuring, and assembling objects known as beans, as well as managing their
 * lifecycle.
 */
public interface ApplicationContext {

  /**
   * Retrieves a bean of the specified type from the container.
   * Throws {@code NoSuchBeanException} when bean with specified type is not exist
   * and {@code NoUniqueBeanException} when there are several beans of specified type.
   *
   * @param beanType the type of the bean to retrieve
   * @param <T>      the generic type of the bean
   * @return the bean of the specified type
   */
  <T> T getBean(Class<T> beanType);

  /**
   * Retrieves a bean with the specified name and type from the container.
   * Throws {@code NoSuchBeanException} when bean with specified type and name is not exist.
   *
   * @param name     the name of the bean to retrieve
   * @param beanType the type of the bean to retrieve
   * @param <T>      the generic type of the bean
   * @return the bean with the specified name and type
   */
  <T> T getBean(String name, Class<T> beanType);

  /**
   * Retrieves a map of all beans of the specified type from the container.
   *
   * @param beanType the type of the beans to retrieve
   * @param <T>      the generic type of the beans
   * @return a map of all beans of the specified type, with the bean names as keys
   */
  <T> Map<String, T> getAllBeans(Class<T> beanType);

}
