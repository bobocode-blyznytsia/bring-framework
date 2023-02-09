package com.bringframework.integration.service;

import com.bringframework.annotation.Autowired;
import com.bringframework.annotation.Component;
import lombok.Getter;

@Component
@Getter
public class ChatService {

  @Autowired
  private GreetingService greetingService;

  @Autowired
  private IMessageService messageService;

}
