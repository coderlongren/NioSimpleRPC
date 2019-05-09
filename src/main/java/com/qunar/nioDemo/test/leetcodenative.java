package com.qunar.nioDemo.test;


import java.util.stream.Stream;

/**
 * 思路：
 *  设dp[i][j]表示s[i:j]中的最长回文子序列长度，则
 *  (1) s[i] == s[j] ==> dp[i][j] = dp[i+1][j-1] + 2;
 *  (2) s[i] != s[j] ==> dp[i][j] = max(dp[i+1][j], dp[i][j-1])
 *  初始化: dp[i][i] = 1
 *  return dp[0][n-1] **/
public class leetcodenative {
    private int[] lengthestSubStringOf(String string) {
        if (string == null) {

        }
        char[] chars = string.toCharArray();
        int[][] dp = new int[chars.length][chars.length];
//        for (int i = 0; i < string.length())
        return new int[3];
    }

    public static void main(String[] args) {
        String[] arr = {"a b c", "d e f"};
        Stream.of(arr).map(str -> Stream.of(str.split(" "))).forEach(item -> {
            System.out.println(item);
//            item.forEach(s -> {
//                System.out.println(s);
//            });
        });

        System.out.println("+++++++++");
        Stream.of(arr).flatMap(strs -> Stream.of(strs.split(" "))).forEach(item -> {
            System.out.println(item);
        });
    }
}

