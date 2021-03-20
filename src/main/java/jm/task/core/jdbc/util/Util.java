package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
//    private static volatile Util instance;
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL =
        "jdbc:mysql://localhost:3306/jmdb?autoReconnect=true&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "buddabar";

//    public static Util getInstance() {
//        Util localInstance = instance;
//        if (localInstance == null) {
//            synchronized (Util.class) {
//                localInstance = instance;
//                if (localInstance == null){
//                    instance = localInstance = new Util();
//                }
//            }
//        }
//        return localInstance;
//    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("База подключена");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Соединение с БД не установлено");
            e.printStackTrace();
        }
        return connection;
    }
}
