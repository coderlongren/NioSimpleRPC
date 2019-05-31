package com.qunar.nioDemo.service;

import com.netflix.hystrix.*;

public class HelloCommand extends HystrixCommand<String> {
    private HelloService service;
    private String name;

    public HelloCommand(HelloService service, String name) {
        super(setter());
        this.service = service;
        this.name = name;
    }

    private static Setter setter(){
        // service group
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("Group-name");
        // service name
        HystrixCommandKey commandKey = HystrixCommandKey.Factory.asKey("hello-service");
        //threadpool name
        HystrixThreadPoolKey threadPoolKey = HystrixThreadPoolKey.Factory.asKey("hello-thread-name");
        // threadpool properties
        HystrixThreadPoolProperties.Setter threadPoolProperties = HystrixThreadPoolProperties.Setter()
                .withCoreSize(10)
                .withMaxQueueSize(Integer.MAX_VALUE)
                .withKeepAliveTimeMinutes(5)
                .withQueueSizeRejectionThreshold(10000);
        HystrixCommandProperties.Setter hystrixCommandProperties = HystrixCommandProperties.Setter()
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD);

        return HystrixCommand.Setter.withGroupKey(groupKey)
                .andCommandKey(commandKey)
                .andThreadPoolKey(threadPoolKey)
                .andThreadPoolPropertiesDefaults(threadPoolProperties)
                .andCommandPropertiesDefaults(hystrixCommandProperties);
    }

    @Override
    protected String run() throws Exception {
        return service.sayHello(name);
    }
}
