package com.bringframework.registry;

import java.lang.reflect.Method;

/**
 * Implementation of {@link BeanDefinition} Represents the definition of a bean that is managed by the Bring IoC
 * container.
 */
//TODO Fix and add java docs

public record ConfigBeanDefinition(Method factoryMethod) {
}
