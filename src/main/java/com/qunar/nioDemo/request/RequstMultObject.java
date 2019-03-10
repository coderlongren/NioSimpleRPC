package com.qunar.nioDemo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (C) XXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午5:54
 **/
@Data
@AllArgsConstructor
public class RequstMultObject implements Serializable {
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
}
