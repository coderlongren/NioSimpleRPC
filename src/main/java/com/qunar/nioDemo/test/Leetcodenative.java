package com.qunar.nioDemo.test;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

/**
 * 思路：
 * 设dp[i][j]表示s[i:j]中的最长回文子序列长度，则
 * (1) s[i] == s[j] ==> dp[i][j] = dp[i+1][j-1] + 2;
 * (2) s[i] != s[j] ==> dp[i][j] = max(dp[i+1][j], dp[i][j-1])
 * 初始化: dp[i][i] = 1
 * return dp[0][n-1]
 **/
public class Leetcodenative {
    private static volatile int num = 1;

    private int[] lengthestSubStringOf(String string) {
        if (string == null) {

        }
        char[] chars = string.toCharArray();
        int[][] dp = new int[chars.length][chars.length];
//        for (int i = 0; i < string.length())
        return new int[3];
    }

    public static void main(String[] args) {
//        String[] arr = {"a b c", "d e f"};
//        Stream.of(arr).map(str -> Stream.of(str.split(" "))).forEach(item -> {
//            System.out.println(item);
////            item.forEach(s -> {
////                System.out.println(s);
////            });
//        });
//
//        System.out.println("+++++++++");
//        Stream.of(arr).flatMap(strs -> Stream.of(strs.split(" "))).forEach(item -> {
//            System.out.println(item);
//        });
        Lock lock = new ReentrantLock();
        Condition odd = lock.newCondition();
        Condition even = lock.newCondition();
        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 2; i++) {
                    if (num % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() + " " + num++);
                        Thread.sleep(1000);
                        even.signal();
                    } else {
                        odd.await();
                    }
                }
                even.signal();
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "B").start();
        new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 2; i++) {
                    if (num % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + " " + num++);
                        Thread.sleep(1000);
                        odd.signal();
                    } else {
                        even.await();
                    }
                }
            } catch (Exception e) {

            } finally {
                lock.unlock();
            }
        }, "A").start();
    }
}

