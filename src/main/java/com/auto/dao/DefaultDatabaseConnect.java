package com.auto.dao;

import java.sql.Connection;

/**
 * @Classname DatabaseConnect
 * @Description TODO
 * @Date 2020/10/3 11:23
 * @Created by Administrator
 */
public class DefaultDatabaseConnect {
    public static Connection getConn() {
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        String className = "org.postgresql.Driver";
        String user = "postgres";
        String password = "332500asd";
        DatabaseConnection databaseConnection = new DatabaseConnection(url, className, user, password);
        return databaseConnection.getConn();
    }
    public static Connection getConn1() {
        String url = "jdbc:postgresql://122.152.214.11:13254/postgres?rewriteBatchedStatements=true";
        String className = "org.postgresql.Driver";
        String user = "postgres";
        String password = "332500asd_+Q";
        DatabaseConnection databaseConnection = new DatabaseConnection(url, className, user, password);
        return databaseConnection.getConn();
    }

}
