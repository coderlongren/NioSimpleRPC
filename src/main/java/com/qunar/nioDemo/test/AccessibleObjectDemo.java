package com.qunar.nioDemo.test;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.AccessibleObject;

public class AccessibleObjectDemo {
    public static void main(String[] args) throws NoSuchMethodException,
            SecurityException, NoSuchFieldException {
        AccessibleObject sampleMethod = SampleClass.class.getMethod("sampleMethod");

        Annotation annotation = sampleMethod.getAnnotation(CustomAnnotation.class);
        if(annotation instanceof CustomAnnotation){
            CustomAnnotation customAnnotation = (CustomAnnotation) annotation;
            System.out.println("name: " + customAnnotation.name());
            System.out.println("value: " + customAnnotation.value());
        }
    }
}

@CustomAnnotation(name="SampleClass",  value = "Sample Class Annotation")
class SampleClass {
    private String sampleField;

    @CustomAnnotation(name="sampleMethod",  value = "Sample Method Annotation")
    public String sampleMethod(){
        return "sample";
    }

    public String getSampleField() {
        return sampleField;
    }

    public void setSampleField(String sampleField) {
        this.sampleField = sampleField;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation {
    public String name();

    public String value();
}

