package com.qunar.nioDemo.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Copyright (C) XXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:54
 **/
@AllArgsConstructor
@NoArgsConstructor
// 请求参数
public class RequestMultObject implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 请求id
     */
    private Long requestId;

    /**
     * 服务提供者接口
     */
    private Class<?> calzz;
    /**
     * 服务的方法名称
     */
    private String methodName;

    /**
     * 参数类型
     */
    private Class<?>[] paramTypes;

    /**
     * 参数
     */
    private Object[] args;

    public RequestMultObject(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object[] args) {
        this.calzz = clazz;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.args = args;
    }
    @Override
    public String toString() {
        return "requestId" + requestId +  ", mathod : " + methodName + ", paramtypes : " + paramTypes + ", args : " + args + " \n";
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Class<?> getCalzz() {
        return calzz;
    }

    public void setCalzz(Class<?> calzz) {
        this.calzz = calzz;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class<?>[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
