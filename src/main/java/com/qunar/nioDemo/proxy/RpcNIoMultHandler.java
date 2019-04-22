package com.qunar.nioDemo.proxy;

import com.qunar.nioDemo.client.RpcNioMultClient;
import com.qunar.nioDemo.request.RequestMultObject;
import com.qunar.nioDemo.request.RpcContainer;
import com.qunar.nioDemo.response.RpcResponseFuture;
import com.qunar.nioDemo.util.SerializeUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


public class RpcNIoMultHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 获得请求id
        Long responseId = RpcContainer.getRequestId();
        // 封装请求对象
        RequestMultObject RequestMultObject = new RequestMultObject(method.getDeclaringClass(), method.getName(),
                method.getParameterTypes(), args);
        RequestMultObject.setRequestId(responseId);

        // 封装设置rpcResponseFuture，主要用于获取返回值
        RpcResponseFuture rpcResponseFuture = new RpcResponseFuture(responseId);
        RpcContainer.addRequestFuture(rpcResponseFuture);

        // 序列化
        byte[] requstBytes = SerializeUtil.serialize(RequestMultObject);
        // 发送请求信息
        RpcNioMultClient rpcNioMultClient = RpcNioMultClient.getInstance();
        rpcNioMultClient.sendMessageToServer(requstBytes);

        // 从ResponseContainer获取返回值
        byte[] responseBytes = rpcResponseFuture.get();
        if (requstBytes != null) {
            RpcContainer.removeResponseAndFuture(responseId);
        }

        // 反序列化获得结果
        Object result = SerializeUtil.unSerialize(responseBytes);
        System.out.println("请求id：" + responseId + " 结果：" + result);
        return result;
    }
}
