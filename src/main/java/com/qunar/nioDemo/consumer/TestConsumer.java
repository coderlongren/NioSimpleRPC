package com.qunar.nioDemo.consumer;

import com.qunar.nioDemo.proxy.RpcProxyFactory;
import com.qunar.nioDemo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumer.class);
    public static void main(String[] args) {
        multipartRpcNio(5);
    }
    public static void multipartRpcNio(int threadNum) {
        long start = System.currentTimeMillis();
        HelloService proxy = RpcProxyFactory.getMultService(HelloService.class);
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                String result = proxy.sayHello("world!");
            }).start();
        }
        LOGGER.info("{} 个客户端连接， 整体耗时: {}", threadNum, System.currentTimeMillis() - start);
//        new Thread(() -> {
//            proxy.sayHello("World");
//        }).start();

    }
}
