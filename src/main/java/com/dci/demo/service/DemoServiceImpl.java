package com.dci.demo.service;

import com.dci.demo.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    private final static Logger log = LoggerFactory.getLogger(DemoServiceImpl.class);

    public String demo(Message message) {

        log.debug("Message: " + message.getMessage());

        return message.getMessage();
    }
}