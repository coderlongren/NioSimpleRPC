package com.qunar.nioDemo.task;

import com.qunar.nioDemo.request.RequestMultObject;
import com.qunar.nioDemo.util.BeanContainer;
import com.qunar.nioDemo.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Copyright (C) XXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:42
 * 线程池任务
 **/
public class RpcNioMultServerTask implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcNioMultServerTask.class);
    private byte[] bytes;

    private SocketChannel channel;

    public RpcNioMultServerTask(byte[] bytes, SocketChannel channel) {
        this.bytes = bytes;
        this.channel = channel;
    }

    /**
     *
     */
    @Override
    public void run() {
        // 接收消息
        if (bytes != null && bytes.length > 0 && channel != null) {
            // 反序列化得到对象
            RequestMultObject requestMultObject = (RequestMultObject) SerializeUtil.unSerialize(bytes);
            requestHandler(requestMultObject, channel);
        }
    }

    // 通过远程字节码 执行method
    private void requestHandler(RequestMultObject requestMultObject, SocketChannel channel) {
        long requestId = requestMultObject.getRequestId();
        Object obj = BeanContainer.getBean(requestMultObject.getCalzz());
        String methodName = requestMultObject.getMethodName();
        Class<?>[] parameterTypes = requestMultObject.getParamTypes();
        Object[] arguments = requestMultObject.getArgs();
        try {
            Method method = obj.getClass().getMethod(methodName, parameterTypes);
            String result = (String) method.invoke(obj, arguments);
            byte[] bytes = SerializeUtil.serialize(result);
            // requestID long 8 byte, 消息长度int 4 byte
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 12);
            buffer.putLong(requestId);
            buffer.putInt(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            // 读buffer缓冲区
            channel.write(buffer);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            LOGGER.error("执行requestHandler 异常", e);
        }

    }
}
