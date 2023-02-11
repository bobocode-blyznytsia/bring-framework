package com.bringframework.registry;

import java.lang.reflect.Method;

/**
 * Represents the definition of a bean that is managed by the Bring IoC container and created by
 * {@link com.bringframework.annotation.Bean} annotation. It provides methods for getting information about the factory
 * method that will be used to create instance of bean.
 *
 * @param factoryMethod factory method that will be used to create instance of bean.
 */
public record ConfigBeanDefinition(Method factoryMethod) {
}
