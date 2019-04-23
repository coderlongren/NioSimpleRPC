package com.qunar.nioDemo.proxy;

import com.qunar.nioDemo.client.RpcNioMultClient;
import com.qunar.nioDemo.request.RequestMultObject;
import com.qunar.nioDemo.request.RpcContainer;
import com.qunar.nioDemo.response.RpcResponseFuture;
import com.qunar.nioDemo.util.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class RpcNIoMultHandler implements InvocationHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcNIoMultHandler.class);
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 获得请求id
        Long responseId = RpcContainer.getRequestId();
        // 封装请求对象
        RequestMultObject requestMultObject = new RequestMultObject(method.getDeclaringClass(), method.getName(),
                method.getParameterTypes(), args);
        requestMultObject.setRequestId(responseId);
        LOGGER.info("封装好了RequestMultObject: " + requestMultObject.toString());
        // 封装设置rpcResponseFuture，主要用于获取返回值
        RpcResponseFuture rpcResponseFuture = new RpcResponseFuture(responseId);
        RpcContainer.addRequestFuture(rpcResponseFuture);

        // 序列化
        byte[] requstBytes = SerializeUtil.serialize(requestMultObject);
        // 发送请求信息
        RpcNioMultClient rpcNioMultClient = RpcNioMultClient.getInstance();
        rpcNioMultClient.sendMessageToServer(requstBytes);

        // 从ResponseContainer获取返回值, 如果远程没有执行完，线程阻塞
        byte[] responseBytes = rpcResponseFuture.get();
        if (requstBytes != null) {
            RpcContainer.removeResponseAndFuture(responseId);
        }

        // 反序列化获得结果
        Object result = SerializeUtil.unSerialize(responseBytes);
        long endTime = System.currentTimeMillis();
        LOGGER.info("请求id：{}, 结果：{}, 整体耗时 : {}", requestMultObject.getRequestId(), (String)result, endTime - startTime);
        return result;
    }
}
