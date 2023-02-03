package com.bringframework.component;

import com.bringframework.annotations.Autowired;
import com.bringframework.annotations.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestComponent {

  @Autowired
  private final TestService testService;

}
