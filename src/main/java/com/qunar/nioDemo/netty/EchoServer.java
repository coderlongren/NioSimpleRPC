package com.qunar.nioDemo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.ArrayList;

public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        EventExecutorGroup businessGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            // SO_BACKLOG 是 TCP accept queue 的大小， 系统会取somaxconn和SO_BACKLOG的最小值来作为Accept Queue的大小，
            // 所以如果想调大这个参数，必须调整两个参数的大小
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 换行符处理器
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            // 字符串处理器
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(businessGroup,"myHandler",
                                    //new LoggingHandler(LogLevel.INFO),
                                    new SimpleServerHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(port).sync();

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8088;
        new EchoServer(port).run();
//        for (int i = 1; i < 10; i++) {
//            if ((i & -i) == i) {
//                System.out.println(i + "是2的幂次方");
//            }
//        }
    }


}
