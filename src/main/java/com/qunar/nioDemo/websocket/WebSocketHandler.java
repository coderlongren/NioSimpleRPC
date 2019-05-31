package com.qunar.nioDemo.websocket;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebSocketHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketHandler.class);
    private WebSocketServerHandshaker handshaker;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Handler FullHttpRequest Message
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest)msg);
        }
        // Handler WebSocket Message
        else if (msg instanceof ) {

        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) {

    }
    private void handleWebSocketRequest(ChannelHandlerContext ctx, )


}
