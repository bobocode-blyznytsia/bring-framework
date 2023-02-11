package com.bringframework.integration;

import com.bringframework.BringApplication;
import com.bringframework.context.ApplicationContext;
import com.bringframework.integration.service.ChatService;
import com.bringframework.integration.service.GreetingService;
import com.bringframework.integration.service.IMessageService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class BringApplicationIT {

  @Test
  void generalBringApplicationTest() {
    ApplicationContext testAppContext = new BringApplication("com.bringframework.integration.service").run();

    ChatService chatService = testAppContext.getBean(ChatService.class);
    GreetingService greetingService = testAppContext.getBean("greetingService", GreetingService.class);
    IMessageService messageService = testAppContext.getBean(IMessageService.class);

    assertNotNull(chatService);
    assertNotNull(greetingService);
    assertNotNull(messageService);
    assertEquals(greetingService, chatService.getGreetingService());
    assertEquals(messageService, chatService.getMessageService());
  }

}
