package com.qunar.nioDemo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(EchoClientHandler.class);
    private final ByteBuf messageBuf;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler(String msg) {
        if (msg == null || msg.length() <= 0) {
            throw new IllegalArgumentException("msg: " + msg);
        }
        byte[] bytes = msg.getBytes();
        messageBuf = Unpooled.buffer(bytes.length);
        messageBuf.writeBytes(bytes);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(messageBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.writeBytes(bytes);
        LOGGER.info("msg: {} {}", byteBuf, bytes);
        String receivedMsg = new String(bytes, "UTF-8");
        LOGGER.info("received message from server : {}", receivedMsg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("exception:", cause);
        ctx.close();
    }
}

