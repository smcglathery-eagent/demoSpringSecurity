package com.dci.demo.controller;

import com.dci.demo.model.Message;
import com.dci.demo.service.DemoService;
import org.easymock.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(EasyMockExtension.class)
public class DemoControllerTest extends EasyMockSupport {

    @TestSubject
    DemoController demoController = new DemoController();

    @Mock
    DemoService demoService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void demo() {

        Message message = new Message();
        message.setMessage("any string");

        demoService.demo(message);
        EasyMock.expectLastCall().andReturn(message.getMessage());

        replayAll();

        String actualMessageText = demoController.demo(message);
        Assertions.assertEquals(message.getMessage(), actualMessageText);

        verifyAll();
    }
}