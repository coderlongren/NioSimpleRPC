package com.qunar.nioDemo.service;

/**
 * Copyright (C) XXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午3:01
 **/
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
