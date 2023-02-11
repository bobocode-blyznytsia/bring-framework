package com.bringframework.scanner;

import com.bringframework.annotation.Bean;
import com.bringframework.annotation.Configuration;
import java.util.Random;
import org.reflections.Reflections;

@Configuration
public class TestConfiguration {

  @Bean
  public Reflections reflections() {
    return Reflections.collect();
  }

  @Bean
  public Random random() {
    return new Random();
  }
}
