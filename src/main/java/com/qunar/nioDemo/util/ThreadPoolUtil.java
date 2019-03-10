package com.qunar.nioDemo.util;

import com.qunar.nioDemo.task.RpcNioMultServerTask;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (C) XXXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:41
 **/
public class ThreadPoolUtil {
    private static volatile ThreadPoolExecutor executor;

    public static void init() {
        if (executor == null) {
            synchronized (ThreadPoolUtil.class) {
                if (executor == null) {
                    executor = new ThreadPoolExecutor(10, 20, 200, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
                }
            }
        }
    }

    public static void addTask(RpcNioMultServerTask task) {
        if (executor == null) {
            init();
        }
        executor.execute(task);
    }
}
