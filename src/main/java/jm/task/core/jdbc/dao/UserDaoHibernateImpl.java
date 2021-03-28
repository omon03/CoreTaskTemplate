package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "user_name VARCHAR(20) NOT NULL , " +
                "user_last_name VARCHAR(20), " +
                "user_age TINYINT)";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Создана таблица \"user\"");
        } catch (Exception e) {
            e.printStackTrace();
//            if (transaction != null) {
//                transaction.rollback();
//            }
        }

        try(Session s = getSessionFactory().openSession()) {
            s.beginTransaction();
            s.createSQLQuery("create table if not exists Users (" +
                    "ID bigint primary key auto_increment," +
                    "Name varchar(15) not null," +
                    "LastName varchar(15)," +
                    "Age tinyint)").executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sql = "DROP TABLE IF EXISTS user";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Удалена таблица \"user\"");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        // auto close session object
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        String jpql = "SELECT a FROM user a";
        List<User> userList = Collections.emptyList();

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery(jpql, User.class).getResultList();
//            userList = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String sql = "TRUNCATE TABLE user";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
