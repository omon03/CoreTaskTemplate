package jm.task.core.jdbc.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;

import javax.imageio.spi.ServiceRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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

    // JDBC connection
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

    // Hibernate connection
    public static SessionFactory getSession() {
        SessionFactory sessionFactory = null;

        Properties properties = new Properties();
        properties.put(Environment.DRIVER, DRIVER);
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USERNAME);
        properties.put(Environment.PASS, PASSWORD);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5InnoDBDialect");
        properties.put(Environment.SHOW_SQL, true);
        properties.put(Environment.HBM2DDL_AUTO, "none");

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(jm.task.core.jdbc.util.Util.class);

        final ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        try {
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            System.out.println("SessionFactory - создан");
        } catch (Exception e) {
            System.out.println("SessionFactory - НЕ создан");
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(serviceRegistry);
        }
        return sessionFactory;
    }
}
