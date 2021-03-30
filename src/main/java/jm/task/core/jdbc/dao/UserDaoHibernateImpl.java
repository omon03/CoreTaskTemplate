package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS USER (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL , " +
                "name VARCHAR(20) NOT NULL , " +
                "lastname VARCHAR(20), " +
//                "user_age TINYINT)";
                "age smallint)";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
//            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            Query query = session.createSQLQuery(sql);
            transaction.commit();
            System.out.println("Создана таблица \"USER\"");
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String sql = "DROP TABLE IF EXISTS USER";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
            System.out.println("Удалена таблица \"user\"");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
//                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User();
        Transaction transaction = null;

        // auto close session object
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
//            session.save(user);
            session.persist(user);
            transaction.commit();
            System.out.printf("User с именем %s добавлен в базу данных", user.getName());
        } catch (Exception e) {
            if (transaction != null) {
//                transaction.rollback();
                System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        String jpql = "SELECT a FROM USER a";
        List<User> userList = Collections.emptyList();

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            userList = session.createQuery(jpql, User.class).getResultList();
//            userList = session.createCriteria(User.class).list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
//                transaction.rollback();
            }
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String sql = "TRUNCATE TABLE USER";

        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery(sql).addEntity(User.class).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (transaction != null) {
//                transaction.rollback();
            }
        }
    }
}
