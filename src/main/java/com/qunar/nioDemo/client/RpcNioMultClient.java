package com.qunar.nioDemo.client;

import com.qunar.nioDemo.request.RpcContainer;
import org.jboss.netty.channel.ServerChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class RpcNioMultClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcNioMultClient.class);
    private static volatile RpcNioMultClient rpcNioClient;

    // 通道管理器
    private Selector selector;

    // 通道
    private SocketChannel channel;

    private String serverIp = "localhost";

    private int port = 8080;

    private RpcNioMultClient() {
        // 初始化client
        initClient();
        new Thread(() -> {
            listen();
        }).start();
    }

    /**
     * 初始化client
     */
    private void initClient() {
        try {
            // 打开一个通道
            channel = SocketChannel.open();
            // 设置为非阻塞通道（异步）
            channel.configureBlocking(false);
            // 获得通道管理器，用于监听通道事件
            selector = Selector.open();
            // 非阻塞，异步建立连接
            channel.connect(new InetSocketAddress(serverIp, port));
            // 非阻塞， finishConnect， 标志client完成连接
            if (channel.isConnectionPending()) {
                channel.finishConnect();
            }
            LOGGER.info(" 客户端初始化完成");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("客户端初始化 失败, ", e);
        }
    }

    /**
     *  单线程，轮训selector
     */
    private void listen() {
        try {
            while (true) {
                channel.register(selector, SelectionKey.OP_READ);
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey item = iterator.next();
                    iterator.remove();
                    LOGGER.info("get a SelectionKey is {}", item);
                    if (item.isReadable()) {
                        // 可读
                        readMessageFromRemote();
                    }
                }

            }
        } catch (Exception e) {
            LOGGER.error("connecting server error, ", e);
        }
    }

    /**
     * 读取
     */
    private void readMessageFromRemote() {
        // 8byte 的requestID
        ByteBuffer byteBuffer = null;
        try {
            // 8 byte 存储 requestID
            byteBuffer = ByteBuffer.allocate(8);
            int readIdCount = channel.read(byteBuffer);
            if (readIdCount < 0) {
                return;
            }
            byteBuffer.flip();
            Long requsetId = byteBuffer.getLong();

            // 4 byte 消息体长度
            byteBuffer = ByteBuffer.allocate(4);
            int readHeadCount = channel.read(byteBuffer);
            if (readHeadCount < 0) {
                return;
            }
            byteBuffer.flip();
            int length = byteBuffer.getInt();

            // length byte 的 消息体
            byteBuffer = ByteBuffer.allocate(length);
            int readBodyCount = channel.read(byteBuffer);
            if (readBodyCount < 0) {
                return;
            }
            byte[] bytes = byteBuffer.array();

            // 将返回值放入指定容器
            RpcContainer.addResponse(requsetId, bytes);
        } catch (IOException e) {
            LOGGER.error("client 读取通道 失败, ", e);
        }
    }

    /**
     *  发送消息给server
     * @param bytes
     * @return
     */
    public boolean sendMessageToServer(byte[] bytes) {
        if (bytes == null) {
            return false;
        }
        // 消息体包括 消息字节数组长度 + 字节数组
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length + 4);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        // 写模式变为 读模式
        byteBuffer.flip();
        try {
            channel.write(byteBuffer);
        } catch (IOException e) {
            LOGGER.error(" client 写通道异常, ", e);
            return false;
        }
        return true;

    }

    /**
     *  双检锁的单例
     * @return
     */
    public static RpcNioMultClient getInstance() {
        if (rpcNioClient == null) {
            synchronized (RpcNioMultClient.class) {
                rpcNioClient = new RpcNioMultClient();
            }
        }
        return rpcNioClient;
    }

}
