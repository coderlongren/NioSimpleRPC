package com.qunar.nioDemo.server;

import com.qunar.nioDemo.task.RpcNioMultServerTask;
import com.qunar.nioDemo.util.ThreadPoolUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Copyright (C) XXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-15 下午3:02
 **/
public class RpcNioMultServer {
    private static final Logger log = LoggerFactory.getLogger(RpcNioMultServer.class);
    // 通道管理器
    private Selector selector;

    public static void startServer() {
        RpcNioMultServer server = new RpcNioMultServer();
        try {
            server.initServer(8080);
        } catch (IOException e) {
            log.error("初始化异常", e);
        }
        server.listen();
    }

    private void listen() {
        log.info("服务端启动成功");
        // 轮训访问 selector
        while (true) {
            try {
                // 当注册的时间到达时,return, 否则 该方法一直阻塞.
                selector.select();
                // 获取事件迭代器
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 防止重复选取这个key
                    iterator.remove();
                    // 客户端请求连接
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        // 获得和客户端连接的通道
                        SocketChannel channel = server.accept();
                        // 设置成非阻塞
                        channel.configureBlocking(false);
                        // 在和客户端连接成功之后，为了可以接收到客户端的信息，需要给通道设置读的权限。
                        channel.register(this.selector, SelectionKey.OP_READ);

                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        byte[] bytes = readMsgFromClient(channel);
                        if (bytes != null && bytes.length > 0) {
                            // 读取之后将任务放入线程池异步返回
                            RpcNioMultServerTask task = new RpcNioMultServerTask(bytes, channel);
                            ThreadPoolUtil.addTask(task);
                        }
                    }

                }
            } catch (IOException e) {
                log.error("异常", e);
            }
        }

    }

    /**
     *  获得一个ServerSocket通道，并对该通道做一些初始化的工作
     * @param port
     */
    public void initServer(int port) throws IOException {
        // 获得一个ServerSocket通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // 设置通道为非阻塞
        serverChannel.configureBlocking(false);
        // 将该通道对应的ServerSocket绑定到port端口
        serverChannel.socket().bind(new InetSocketAddress(port));
        // 获得一个通道管理器
        this.selector = Selector.open();
        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
        // 当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     *  从client端读
     * @param channel
     * @return
     */
    public byte[] readMsgFromClient(SocketChannel channel) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        try {
            // 首先读取消息头（自己设计的协议头，此处是消息体的长度）
            int headCount = channel.read(byteBuffer);
            if (headCount < 0) {
                return null;
            }
            byteBuffer.flip();
            int length = byteBuffer.getInt();
            // 读取消息体
            byteBuffer = ByteBuffer.allocate(length);
            int bodyCount = channel.read(byteBuffer);
            if (bodyCount < 0) {
                return null;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            log.error("读取数据异常", e);
            return null;
        }
    }
}
