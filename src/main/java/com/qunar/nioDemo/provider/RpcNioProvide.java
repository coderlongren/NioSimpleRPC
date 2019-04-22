package com.qunar.nioDemo.provider;

import com.qunar.nioDemo.server.RpcNioMultServer;
import com.qunar.nioDemo.service.HelloService;
import com.qunar.nioDemo.service.HelloServiceImpl;
import com.qunar.nioDemo.util.BeanContainer;

/**
 *  server provider
 */
public class RpcNioProvide {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        // 需要暴露的服务放入到容器中
        BeanContainer.addBean(helloService.getClass(), helloService);
        startMultiNioServer();
    }

    /**
     * 启动NIO服务
     */
    private static void startMultiNioServer() {
        new Thread(() -> {
            new RpcNioMultServer().startServer();
        }).start();
    }
}
