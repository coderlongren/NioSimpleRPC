package com.qunar.nioDemo.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *  Test
 */
public class TestServer {
    private int a = 0;
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clazz = TestServer.class;
        Method method = clazz.getDeclaredMethod("test", String.class);
//        Class<?> paramTypes =
        method.invoke(new TestServer(), "aa");

    }
    public void test(String a) {
        System.out.println("test");
    }
}
