package com.qunar.nioDemo.util;

import java.util.concurrent.ConcurrentHashMap;

public class BeanContainer {
    private static final ConcurrentHashMap<Class<?>, Object> container = new ConcurrentHashMap<>();

    /**
     *  把服务放进容器
     *
     * @param c 目标类的字节码
     * @param o 目标对象
     */
    public static boolean addBean(final Class<?> c, final Object o) {
        container.put(c, o);
        return true;
    }

    /**
     * 从容器获取服务
     *
     * @param c 字节码±
     * @return
     */
    public static Object getBean(final Class<?> c) {
        return container.get(c);
    }
}
