package com.qunar.nioDemo.request;

import com.qunar.nioDemo.response.RpcResponseFuture;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Copyright (C) XXXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:58
 *  RPC容器，存储发送RPC请求时的请求对象，以及对应的返回值
 **/
public class RpcContainer {
    // 存储请求对象的容器
    private static ConcurrentHashMap<Long, RpcResponseFuture> requestFuture = new ConcurrentHashMap<>();

    // 返回值的容器
    private static ConcurrentHashMap<Long, byte[]> responseContainer = new ConcurrentHashMap<>();

    // 递增的请求ID
    private static volatile AtomicLong requestId = new AtomicLong(0);

    // 获取一个递增的请求ID, AtomicLong CAS保证全局唯一
    public static long getRequestId() {
        return requestId.getAndIncrement();
    }

    public static void addResponse(Long requestId, byte[] responseBody) {
        responseContainer.put(requestId, responseBody);
        RpcResponseFuture responseFuture = requestFuture.get(requestId);
        // 这个RPC调用完成
        responseFuture.rpcIsDone();
    }



}
