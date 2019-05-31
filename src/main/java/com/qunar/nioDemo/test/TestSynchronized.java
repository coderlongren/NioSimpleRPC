package com.qunar.nioDemo.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestSynchronized {
    private final static Object object = new Object();
    private Object o1 = new Object();
    public void minus() {
        synchronized (object) {
            int count = 5;
            for (int i = 0; i < 5; i++) {
                count--;
                System.out.println(Thread.currentThread().getName() + " - " + count);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
            }
        }
    }

    public synchronized void minus2() {
        int count = 5;
        for (int i = 0; i < 5; i++) {
            count--;
            System.out.println(Thread.currentThread().getName() + " - " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    public void minu3() {
        synchronized (this) {
            int count = 5;
            for (int i = 0; i < 5; i++) {
                count--;
                System.out.println(Thread.currentThread().getName() + " - " + count);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition oneCondition = lock.newCondition();
        Condition twoCondition = lock.newCondition();
        lock.lock();
        new Thread(() -> {
            for (;;) {
                try {
                    System.out.println("join");
                    Thread.currentThread().join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "my threadOne").start();
        oneCondition.await();
    }

}
