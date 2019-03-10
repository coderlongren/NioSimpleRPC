package com.qunar.nioDemo.task;

import java.nio.channels.SocketChannel;

/**
 * Copyright (C) XXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:42
 * 线程池任务
 **/
public class RpcNioMultServerTask implements Runnable {
    private byte[] bytes;

    private SocketChannel channel;

    public RpcNioMultServerTask(byte[] bytes, SocketChannel channel) {
        this.bytes = bytes;
        this.channel = channel;
    }

    @Override
    public void run() {

    }
}
