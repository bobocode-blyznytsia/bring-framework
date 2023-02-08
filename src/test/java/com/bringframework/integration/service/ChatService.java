package com.bringframework.integration.service;

import com.bringframework.annotations.Autowired;
import com.bringframework.annotations.Component;
import lombok.Getter;

@Component
@Getter
public class ChatService {

  @Autowired
  private GreetingService greetingService;

  @Autowired
  private IMessageService messageService;

}
