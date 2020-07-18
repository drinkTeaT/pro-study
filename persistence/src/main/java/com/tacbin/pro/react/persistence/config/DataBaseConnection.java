package com.tacbin.pro.react.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Description 连接mysql
 * Author liuweibin
 **/
public class DataBaseConnection {
    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/springboot";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://rm-wz96378h5d05nb6f6.mysql.rds.aliyuncs.com:3306/ttai?autoReconnect=true&tinyInt1isBit=false&useUnicode=true&useSSL=true";


    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "develop";
    static final String PASS = "U2@amhtEs6YhU6";
    private static ThreadLocal<Connection> threadLocal = ThreadLocal.withInitial(() -> {
        Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    });

    /**
     * 获取mysql连接
     *
     * @return
     */
    public static Connection getConnection() {
        return threadLocal.get();
    }
}
