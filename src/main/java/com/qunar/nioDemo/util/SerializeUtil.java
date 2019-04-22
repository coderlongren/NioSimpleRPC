package com.qunar.nioDemo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Copyright (C) XXXX.com - All Rights Reserved.
 *
 * @author Sailong Ren
 * @date 19-3-10 下午2:59
 *  序列化的工具类, 提供简单的序列化和反序列化
 **/
public class SerializeUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(SerializeUtil.class);
    public static byte[] serialize(Object obj) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            byte[] bytes = bos.toByteArray();
            return bytes;
        } catch (IOException e) {
            LOGGER.error("序列化失败,", e);
            return null;
        }
    }

    public static Object unSerialize(byte[] bytes) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            return ois.readObject();
        } catch (IOException ioException) {
            LOGGER.error("反序列化异常, ", ioException);
            return null;
        } catch (ClassNotFoundException e) {
            LOGGER.error("反序列化异常", e);
            return null;
        }
    }
}
