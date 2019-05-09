package com.qunar.nioDemo.test;

public class Test {
    public static void main(String[] args) {

        final TestSynchronized test = new TestSynchronized();
        final TestSynchronized test1 = new TestSynchronized();
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                test.minu3();
            }
        });

        Thread thread2 = new Thread(new Runnable() {

            @Override
            public void run() {
//                TestSynchronized.minus();

                test.minus2();
            }
        });

        thread1.start();
        thread2.start();

    }
}
