package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private static Connection connection;

    public UserDaoJDBCImpl() { }

    @Override
    public void createUsersTable() throws SQLException {
        if (connection == null) {
            connection = getConnection();
        }
        connection.setAutoCommit(false);

        try (Statement statement = connection.createStatement();) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "user_name VARCHAR(20) NOT NULL , " +
                    "user_last_name VARCHAR(20), " +
                    "user_age TINYINT)");
            connection.commit();
            System.out.println("Создана таблица \"user\"");
        } catch (SQLException throwables) {
            connection.rollback();
            throwables.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() throws SQLException {
        if (connection == null) {
            connection = getConnection();
        }
        connection.setAutoCommit(false);

        try (Statement statement = connection.createStatement();) {
            String sqlCommand = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sqlCommand);
            connection.commit();
            System.out.println("Удалена таблица \"user\"");
        } catch (SQLException throwables) {
            connection.rollback();
            throwables.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {

        String sqlCommand = "INSERT INTO user (user_name, user_last_name, user_age) VALUES ((?), (?), (?))";
        if (connection == null) {
            connection = getConnection();
        }
        connection.setAutoCommit(false);

        try (PreparedStatement st = connection.prepareStatement(sqlCommand)) {
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
            connection.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        String sql = "DELETE FROM user WHERE id = ?";
        if (connection == null) {
            connection = getConnection();
        }
        connection.setAutoCommit(false);
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        List<User> list = new ArrayList<>();
        if (connection == null) {
            connection = getConnection();
        }
        connection.setAutoCommit(false);

        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User tUser = new User();
                tUser.setId(resultSet.getLong("id"));
                tUser.setName(resultSet.getString("user_name"));
                tUser.setLastName(resultSet.getString("user_last_name"));
                tUser.setAge(resultSet.getByte("user_age"));
                list.add(tUser);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        if (connection == null) {
            connection = getConnection();
        }
        connection.setAutoCommit(false);

        try (Statement st = connection.createStatement()) {
            st.execute("TRUNCATE TABLE user");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
        }
    }
}
