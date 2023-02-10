package com.bringframework;

import com.bringframework.annotation.Bean;
import com.bringframework.annotation.Qualifier;
import java.util.Random;
import org.reflections.Reflections;

//TODO Remove. Added for demonstration purpose
@com.bringframework.annotation.Configuration
public class Configuration {

  @Bean
  public Reflections reflections() {
    return Reflections.collect();
  }
  @Bean
  public Reflections reflections2() {
    return Reflections.collect();
  }

  @Bean
  org.reflections.Configuration configuration(@Qualifier("reflections")Reflections reflections) {
    return reflections.getConfiguration();
  }
  @Bean
  org.reflections.Configuration configuration2(@Qualifier("reflections2") Reflections reflections) {
    return reflections.getConfiguration();
  }

  @Bean
  public Random random() {
    return new Random();
  }

  @Bean
  public SomeClass someClass1() {
    return new SomeClass();
  }
  @Bean
  public SomeClass someClass2() {
    return new SomeClass();
  }
  @Bean
  public SomeClass2 some(@Qualifier("someClass1")SomeClass someClass1,
                         @Qualifier("someClass1")SomeClass someClass3,
                         @Qualifier("someClass2")SomeClass someClass2) {
    return new SomeClass2(someClass1, someClass2, someClass3);
  }


  @Bean
  public RecursiveBean recursiveBean1() {
    return new RecursiveBean(null);
  }

  @Bean
  public RecursiveBean recursiveBean2(@Qualifier("recursiveBean1") RecursiveBean recursiveBean) {
    return new RecursiveBean(recursiveBean);
  }

  @Bean
  public RecursiveBean recursiveBean3(@Qualifier("recursiveBean2") RecursiveBean recursiveBean) {
    return new RecursiveBean(recursiveBean);
  }

  @Bean
  public RecursiveBean recursiveBean4(@Qualifier("recursiveBean3") RecursiveBean recursiveBean) {
    return new RecursiveBean(recursiveBean);
  }

  @Bean
  public RecursiveBean recursiveBean5(@Qualifier("recursiveBean4") RecursiveBean recursiveBean) {
    return new RecursiveBean(recursiveBean);
  }

  @Bean
  public RecursiveBean recursiveBean6(@Qualifier("recursiveBean5") RecursiveBean recursiveBean) {
    return new RecursiveBean(recursiveBean);
  }

}
