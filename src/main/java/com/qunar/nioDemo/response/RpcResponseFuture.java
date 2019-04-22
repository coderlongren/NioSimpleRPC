package com.qunar.nioDemo.response;

import com.qunar.nioDemo.request.RpcContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (C) XXXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:58
 * 请求数据获取类，用于挂起或者线程或者 唤醒被挂起的线程
 **/

public class RpcResponseFuture {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcResponseFuture.class);
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Long requstId;

    public RpcResponseFuture(Long requstId) {
        this.requstId = requstId;
    }

    public byte[] get() {
        byte[] bytes = RpcContainer.getResponse(requstId);
        if (bytes == null || bytes.length < 0) {
            lock.lock();
            try {
                LOGGER.info("请求ID : {}, 请求未返回线程挂起", requstId);
                condition.await();
            } catch (InterruptedException e) {
                LOGGER.error("中断异常, ", e);
            } finally {
                lock.unlock();
            }
        }
        LOGGER.info("请求ID : {}, 请求结果返回，线程挂起结束", requstId);
        return RpcContainer.getResponse(requstId);
    }

    public void rpcIsDone() {
        lock.lock();
        try {
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public Long getRequstId() {
        return requstId;
    }

    public void setRequstId(Long requstId) {
        this.requstId = requstId;
    }
}
