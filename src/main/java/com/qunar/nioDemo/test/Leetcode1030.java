package com.qunar.nioDemo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Leetcode1030 {
    private static final Logger log = LoggerFactory.getLogger(Leetcode1030.class);
    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        List<Cell> list = new ArrayList<>();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                list.add(new Cell(Math.abs(r0 - i) + Math.abs(c0 - j),i, j));
            }
        }
        Collections.sort(list, new Comparator<Cell>() {
            @Override
            public int compare(Cell o1, Cell o2) {
                return o1.dis - o2.dis;
            }
        });
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i][0] = list.get(i).r;
            res[i][1] = list.get(i).l;
        }
        return res;
    }
    class Cell{
        int dis;
        int r;
        int l;
        Cell(int dis, int r, int l) {
            this.dis = dis;
            this.r = r;
            this.l = l;
        }
    }

    public static void main(String[] args) {
        int i = 0;
        log.info("aaaa, i = {}", i);
    }
}
