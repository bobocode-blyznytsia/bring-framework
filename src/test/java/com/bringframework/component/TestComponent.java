package com.bringframework.component;

import com.bringframework.annotations.Autowired;
import com.bringframework.annotations.Component;
import lombok.Data;

@Data
@Component
public class TestComponent {

  @Autowired
  private final TestService testService;
}
