package com.qunar.nioDemo.proxy;

import java.lang.reflect.Proxy;

/**
 * service的代理工厂
 */
public class RpcProxyFactory {
    public static <T> T getMultService(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, new RpcNIoMultHandler());
//        return Proxy.newProxyInstance()
    }
}
