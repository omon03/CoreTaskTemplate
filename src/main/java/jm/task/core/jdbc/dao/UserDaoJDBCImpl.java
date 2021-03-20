package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() { }

    public void createUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                    "user_name VARCHAR(20) NOT NULL , " +
                    "user_last_name VARCHAR(20), " +
                    "user_age TINYINT)");
            System.out.println("Создана таблица \"user\"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            String sqlCommand = "DROP TABLE IF EXISTS user";
            statement.executeUpdate(sqlCommand);
            System.out.println("Удалена таблица \"user\"");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlCommand = "INSERT INTO user (user_name, user_last_name, user_age) VALUES ((?), (?), (?))";

        try (PreparedStatement st = Util.getConnection().prepareStatement(sqlCommand)) {
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM user WHERE id = ?";
        try (PreparedStatement st = Util.getConnection().prepareStatement(sql)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Statement st = Util.getConnection().createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User tUser = new User();
                tUser.setId(resultSet.getLong("id"));
                tUser.setName(resultSet.getString("user_name"));
                tUser.setLastName(resultSet.getString("user_last_name"));
                tUser.setAge(resultSet.getByte("user_age"));
                list.add(tUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement st = Util.getConnection().createStatement()) {
            st.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
