package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoHibernateImpl();

    public UserServiceImpl() {
    }

    public void createUsersTable() {
        try {
            userDao.createUsersTable();
        } catch (SQLException e) {
            System.out.println("createUsersTable: FAIL");
        }
    }

    public void dropUsersTable() {
        try {
            userDao.dropUsersTable();
        } catch (SQLException e) {
            System.out.println("dropUsersTable: FAIL");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            userDao.saveUser(name, lastName, age);
        } catch (SQLException e) {
            System.out.println("saveUser: FAIL");
        }
    }

    public void removeUserById(long id) {
        try {
            userDao.removeUserById(id);
        } catch (SQLException e) {
            System.out.println("removeUserById: FAIL");
        }
    }

    public List<User> getAllUsers() {
        try {
            return userDao.getAllUsers();
        } catch (SQLException e) {
            System.out.println("getAllUsers: FAIL");
        }
        return Collections.emptyList();
    }

    public void cleanUsersTable() {
        try {
            userDao.cleanUsersTable();
        } catch (SQLException e) {
            System.out.println("cleanUsersTable: FAIL");
        }
    }
}
