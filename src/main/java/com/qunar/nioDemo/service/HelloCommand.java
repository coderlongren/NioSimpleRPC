package com.qunar.nioDemo.service;

import com.netflix.hystrix.*;

public class HelloCommand extends HystrixCommand<String> {
    private HelloService service;


    private static class Setter {
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("Group-name");
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("hello-service");
        HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("hello-thread-name");
        HystrixThreadPoolProperties threadPoolProperties = HystrixThreadPoolProperties.Setter()
                .withCoreSize(10)
                .withMaxQueueSize(10000)
                .withKeepAliveTimeMinutes(1000)
                .withQueueSizeRejectionThreshold()
    }

    @Override
    protected String run() throws Exception {
        return null;
    }
}
