package com.qunar.nioDemo.test;

import com.google.common.cache.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestCacheJSR {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestCacheJSR.class);
    private static final AtomicInteger count = new AtomicInteger(1);

    public static void main(String[] args) {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .concurrencyLevel(8)
                .expireAfterWrite(3, TimeUnit.SECONDS)
                .initialCapacity(10)
                .maximumSize(100)
                .recordStats()
                .removalListener(notifyfation -> {
                    LOGGER.info("{} is removed", notifyfation.getKey());
                }).build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        String val = key + " val" + count.getAndIncrement();
                        LOGGER.info("load the key : {} ; val : {}", key, val);
                        return val;
                    }
                });

        for (int i = 0; i < 20; i++) {
            try {
                String val = cache.get("Hello");
                LOGGER.info("load the val : {}", val);
                Thread.sleep(1000);
            } catch (ExecutionException e) {
                LOGGER.error("load key error : ", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
