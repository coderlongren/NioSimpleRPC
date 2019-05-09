package com.qunar.nioDemo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Copyright (C) XXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午3:01
 **/
public class HelloServiceImpl implements HelloService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloServiceImpl.class);
    @Override
    public String sayHello(String name) {
        try {
            // 模仿阻塞一秒
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            LOGGER.error("中断, ", e);
        }
        return "Hello, " + name;
    }
}