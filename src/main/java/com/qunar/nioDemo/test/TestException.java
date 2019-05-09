package com.qunar.nioDemo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.sql.SQLException;

public class TestException {
    public static void main(String[] args) {
        try {
//            test1();
            String path = "/Users/sailongren/GitRes/nioDemo/src/main/resources/file.txt";
            testCatFile(path);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void test1() throws IOException, SQLException {
        InputStream inputStream = new FileInputStream("/Users/sailongren/GitRes/nioDemo/src/main/resources/file.txt");
        byte[] bytes = new byte[2];
//        while (inputStream.read(bytes) != -1) {
//            System.out.println("read 一次" + new String(bytes).toString());
//            bytes = new byte[2];
//        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        FileChannel channel = ((FileInputStream) inputStream).getChannel();
        while (channel.read(byteBuffer) != -1) {
            System.out.println("第一次读");
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.println((char)byteBuffer.get());
            }
            byteBuffer.clear();
        }
    }
    private static void testCatFile(String file) throws Exception {
        WritableByteChannel target = Channels.newChannel(System.out);
        // Sysout.out是一个rintStream extends FilterOutputStream
        FileChannel channel = new FileInputStream(file).getChannel();
        channel.transferTo(0, channel.size(), target);
    }
}
