package com.qunar.nioDemo.test;

import org.checkerframework.checker.units.qual.A;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test <E>{
    public static void test() {
        int i = 0;
        try {
            int a = i / 0;
        } catch (Exception e) {
            throw new RuntimeException();
        } finally {
            throw new RuntimeException();
        }
    }
    public static void main1(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();;
        lock.lock();
        Thread.sleep(1000);
        condition.signal();
        lock.unlock();
        new Thread(() -> {
            lock.lock();
            try {
                condition.await();
                System.out.println("从await解除");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }, "MyThread").start();
        System.out.println("main结束");

    }

    public static void main(String[] args) throws InterruptedException {
//        List<User> list = new ArrayList<>();
//        int count = 1;
//        while (true) {
//            User user = new User(genarateBigStr(), new Random().nextInt(100000));
//            list.add(user);
//            System.out.println("添加了一个User" + count);
//            Thread.sleep(6);
//            count++;
//            if (count == 3000) {
//                list = new ArrayList<>();
//                count = 1;
//            }
//        }
        while (true) {
            testTime();
        }
    }
    private static void testTime() throws InterruptedException {
        Thread.sleep(3000);
    }

    private static String genarateBigStr() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < 10000; i++) {
            stringBuilder.append("ssdfsdfsdf" + i);
        }
        return stringBuilder.toString();
    }
    public static void main2(String[] args) {
        List<Integer> list = new ArrayList<>();
//        add(list, "ss");
//        System.out.println(list.size());
//        Integer s = list.get(0);
//        System.out.println(s);
//        System.out.println(list.get(0));
//        List<?> list1 = new ArrayList<>();
//        list1.add(null);
//        Collection<?> collection = new ArrayList<>();
//        collection.add(new Integer(3));
        // Why generic array creation is illegal - won't compile!
////        List<String>[] stringLists = new List<String>[1];  // (1)
//        List<Integer> intList = List.of(42);               // (2)
//        Object[] objects = stringLists;                    // (3)
//        objects[0] = intList;                              // (4)
//        String s = stringLists[0].get(0);                  // (5)
        Lock lock = new ReentrantLock();
        Condition one = lock.newCondition();
        Condition two = lock.newCondition();
        AtomicInteger count = new AtomicInteger(1);
        Condition three = lock.newCondition();
        new Thread(() -> {
            final int num = 1;
            while (true) {
                lock.lock();
                if (count.get() == 1) {
                    count.set(2);
                    System.out.println("A" + "name : " + Thread.currentThread().getName());
                    two.signal();
                } else {
                    try {
                        one.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.unlock();

            }
        }, "Thread_1").start();
        new Thread(() -> {
            final int num = 1;
            while (true) {
                lock.lock();
                if (count.get() == 2) {
                    count.set(3);
                    System.out.println("B" + "name : " + Thread.currentThread().getName());
                    three.signal();
                } else {
                    try {
                        two.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.unlock();

            }
        }, "Thread_2").start();
        new Thread(() -> {
            final int num = 1;
            while (true) {
                lock.lock();
                if (count.get() == 3) {
                    count.set(1);
                    System.out.println("C" + "name : " + Thread.currentThread().getName());
                    one.signal();
                } else {
                    try {
                        three.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.unlock();

            }
        }, "Thread_3").start();

    }

    /**
     * 泛型方法的 consumer
     *
     * @param list
     * @param i
     * @param j
     */
    private static void add(List<?> list, int i, int j) {
        addHelper(list, i, j);
    }

    private static <E> List<E> addHelper(List<E> list, int i, int j) {
        E temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
        return list;
    }

    private Set<E> union(Set<E> s1, Set<E> s2) {
        s1.addAll(s2);
        return s1;
    }
    class MyThread extends Thread {

    }
}
