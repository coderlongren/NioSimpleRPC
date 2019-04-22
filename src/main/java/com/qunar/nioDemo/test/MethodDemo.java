package com.qunar.nioDemo.test;

import java.lang.reflect.Method;

public class MethodDemo {

    public static void main(String[] args) throws NoSuchMethodException {
        Method[] methods = SampleClass.class.getMethods();
        Method method = SampleClass.class.getMethod("setSampleField", String.class);
        Class declaringClass = method.getDeclaringClass();
        Class[] paramTypes = method.getParameterTypes();
        for (Class clazz : paramTypes) {
            System.out.println(clazz.getName());
        }
        System.out.println(declaringClass.getName());
    }
}

class SampleClass1 {
    private String sampleField;

    public String getSampleField() {
        return sampleField;
    }

    public void setSampleField(String sampleField) {
        this.sampleField = sampleField;
    }
}

