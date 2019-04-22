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
@Data
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
        return "mathod : " + methodName + ", paramtypes : " + paramTypes + ", args : " + args + " \n";
    }
}
