package com.qunar.nioDemo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            EchoServerHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String receivedMsg = new String(bytes, "UTF-8");
        if (receivedMsg.equals("Hello")) {
            LOGGER.info("server received message  : {}", receivedMsg);
            String responseStr = System.currentTimeMillis() + " from server";
            Thread.sleep(5000);
            LOGGER.info("服务端需要返回的 : {}", responseStr);
            byte[] resBytes = responseStr.getBytes();
            ByteBuf responseBuf = Unpooled.buffer(resBytes.length);
            responseBuf.writeBytes(resBytes);
            ctx.write(responseBuf);
        } else {
            LOGGER.error("不是认证的客户端");
            String errMsg = receivedMsg + "BAD Req";
            byte[] resBytes = errMsg.getBytes();
            ByteBuf resBuf = Unpooled.buffer(resBytes.length);
            resBuf.writeBytes(resBytes);
            ctx.writeAndFlush(resBuf);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        LOGGER.error("unChecked Exception ", cause);
        ctx.close();
    }
}
