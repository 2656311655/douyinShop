package com.auto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Description:
 * @Param:
 * @Auther: zl
 * @Date: 2020/2/25 20:37
 */
public class DatabaseConnection {
    private Connection conn = null;
    private String user;
    private String password;

    public DatabaseConnection(String url, String className, String user, String password) {
        this.user = user;
        this.password = password;
        connect(url, className);
    }

    public boolean connect(String url, String className) {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        //url="jdbc:sqlite://E:\\data\\local\\file.db";
        System.out.println("正在连接数据库......");
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public Connection getConn() {
        return conn;
    }

    public boolean close() {
        try {
            if (conn != null) {
                conn.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        // DatabaseConnection obj = new DatabaseConnection();
    }
}
