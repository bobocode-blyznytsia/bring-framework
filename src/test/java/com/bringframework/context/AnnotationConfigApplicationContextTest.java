package com.bringframework.context;

import com.bringframework.exception.NoSuchBeanException;
import com.bringframework.exception.NoUniqueBeanException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AnnotationConfigApplicationContextTest {

    private AnnotationConfigApplicationContext applicationContext;
    private Map<String, Object> testBeans;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        applicationContext = new AnnotationConfigApplicationContext("testPackageName");
        initTestBeans();
        setTestBeansToBeanFiledAppContext();
    }

    @Test
    void shouldReturnBeanOfSpecifiedType() {
        String actualBean = applicationContext.getBean(String.class);
        assertEquals("Bobo", actualBean);
    }

    @Test
    void shouldThrowNoSuchBeanExceptionWhenThereIsNoBeanWithSpecifiedType() {
        NoSuchBeanException actualException =
                assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean(Boolean.class));
        assertEquals("Bean with type Boolean does not exist!", actualException.getMessage());
    }

    @Test
    void shouldThrowNoUniqueBeanExceptionWhenThereIsMoreThanOneBeanPresentOfSpecifiedType() {
        NoUniqueBeanException actualException =
                assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(Integer.class));
        assertEquals(""" 
                     There are several beans with type Integer, to get bean use method with bean name parameter:
                        getBean(String name, Class<T> beanType)""", actualException.getMessage());
    }

    @Test
    void shouldReturnBeanOfSpecifiedTypeAndName() {
        Integer actualBean = applicationContext.getBean("intBean2", Integer.class);
        assertEquals(112, actualBean);
    }

    @Test
    void shouldThrowNoSuchBeanExceptionWhenThereIsNoBeanWithSpecifiedTypeAndName() {
        NoSuchBeanException actualException =
                assertThrows(NoSuchBeanException.class,
                        () -> applicationContext.getBean("beanFromAnotherPlanet", Integer.class));
        assertEquals("Bean with name beanFromAnotherPlanet, and type Integer does not exist!",
                actualException.getMessage());
    }

    @Test
    void shouldReturnAllBeansOfSpecificType() {
        Map<String, Number> actualBeans = applicationContext.getAllBeans(Number.class);
        assertEquals(3, actualBeans.size());
        assertFalse(actualBeans.containsKey("stringBean"));
        assertTrue(actualBeans.containsKey("intBean"));
        assertTrue(actualBeans.containsKey("intBean2"));
        assertTrue(actualBeans.containsKey("floatBean"));
    }

    @Test
    void shouldReturnEmptyMapOfBeansWhenThereIsNoBeansWithSpecifiedType() {
        Map<String, LocalDateTime> actualBeans = applicationContext.getAllBeans(LocalDateTime.class);
        assertTrue(actualBeans.isEmpty());
    }

    private void initTestBeans() {
        testBeans = new HashMap<>();
        testBeans.put("stringBean", "Bobo");
        testBeans.put("intBean", 111);
        testBeans.put("intBean2", 112);
        testBeans.put("floatBean", 113.5);
    }

    // this is temporary solution for tests
    private void setTestBeansToBeanFiledAppContext() throws IllegalAccessException {
        Field beansField = Arrays.stream(applicationContext.getClass().getDeclaredFields())
                .filter(field -> field.getName().equals("beans"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Field beans was not found"));
        beansField.setAccessible(true);
        beansField.set(applicationContext, testBeans);
    }

}
