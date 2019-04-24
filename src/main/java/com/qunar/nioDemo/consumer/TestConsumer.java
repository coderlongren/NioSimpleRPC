package com.qunar.nioDemo.consumer;

import com.google.common.collect.Lists;
import com.qunar.nioDemo.proxy.RpcProxyFactory;
import com.qunar.nioDemo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.*;

public class TestConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestConsumer.class);
    // 十个线程 等待主线程结束
    private static final CountDownLatch coutDownLatch = new CountDownLatch(100);

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(200, 300, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<>());

    public static void main(String[] args) {
        multipartRpcNio(10);

    }
    // 改为线程池执行
    public static void multipartRpcNio(int threadNum) {

        long start = System.currentTimeMillis();
        HelloService proxy = RpcProxyFactory.getMultService(HelloService.class);
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//
//        });
        List<CompletableFuture> allTask = Lists.newArrayList();
        for (int i = 0; i < threadNum; i++) {
//            Future oneFuture = threadPool.submit(() -> {
//                proxy.sayHello("World");
////            });
            CompletableFuture<String> oneTask = CompletableFuture.supplyAsync(() -> proxy.sayHello("World"), threadPool);
            allTask.add(oneTask);
        }
        allTask.stream().forEach(TestConsumer::accept);
        LOGGER.info("{} 个客户端连接， 整体耗时: {}", threadNum, System.currentTimeMillis() - start);

    }

    private static void accept(CompletableFuture item) {
        try {
            item.get(2, TimeUnit.SECONDS);
//            coutDownLatch.countDown();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
