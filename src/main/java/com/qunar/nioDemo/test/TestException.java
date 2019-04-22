package com.qunar.nioDemo.test;

import java.io.IOException;
import java.sql.SQLException;

public class TestException {
    public static void main(String[] args) {
        try {
            test1();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private static void test1() throws IOException, SQLException {

    }
}
