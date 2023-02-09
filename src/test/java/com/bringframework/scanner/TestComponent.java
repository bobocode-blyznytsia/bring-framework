package com.bringframework.scanner;

import com.bringframework.annotation.Autowired;
import com.bringframework.annotation.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TestComponent {

  @Autowired
  private final TestService testService;

}
