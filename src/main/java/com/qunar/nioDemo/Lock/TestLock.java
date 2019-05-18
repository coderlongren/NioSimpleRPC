package com.qunar.nioDemo.Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {
    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock(true);
        LockSupport.park(Thread.currentThread());
        new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(Integer.MAX_VALUE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(1000);
        lock.lock();
        System.out.println("主线程 结束了");
    }
}
