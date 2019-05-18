package com.qunar.nioDemo.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPServerHandler extends ChannelInboundHandlerAdapter {
    private int count = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(TCPServerHandler.class);
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 1
//        ByteBuf buf = (ByteBuf) msg;
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//        String receivedMsg = new String(bytes, "UTF-8");
        // 2

        String receivedMsg = (String) msg;
        LOGGER.info("服务端接收到请求 : " + receivedMsg + "order is " + count++);
        String resStr = System.currentTimeMillis() + "from remote server \n";
        ByteBuf resBuf = Unpooled.copiedBuffer(resStr.getBytes());
        ctx.writeAndFlush(resBuf);
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
