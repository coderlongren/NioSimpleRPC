package com.qunar.nioDemo.consumer;

import com.qunar.nioDemo.proxy.RpcProxyFactory;
import com.qunar.nioDemo.service.HelloService;

public class TestConsumer {
    public static void main(String[] args) {
        multipartRpcNio();
    }
    public static void multipartRpcNio() {
        HelloService proxy = RpcProxyFactory.getMultService(HelloService.class);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                String result = proxy.sayHello("world!");
            }).start();
        }
//        new Thread(() -> {
//            proxy.sayHello("World");
//        }).start();

    }
}
