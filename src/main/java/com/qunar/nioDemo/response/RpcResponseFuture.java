package com.qunar.nioDemo.response;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Copyright (C) XXXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:58
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


}
