package com.qunar.nioDemo.response;

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
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private Long requstId;

    public RpcResponseFuture(long id) {
        this.requstId = id;
    }

    public byte[] get() {
        byte[] bytes = Rp
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
