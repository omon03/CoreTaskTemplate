package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

/**
 *  Создание таблицы User(ов)
 *  Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 *  Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 *  Очистка таблицы User(ов)
 *  Удаление таблицы
 */
public class Main {
    public static void main(String[] args) {
        UserService us = new UserServiceImpl();

        us.createUsersTable();

        String name = "Ilya";
        String lastName = "Votin";
        byte age = 30;

        us.saveUser(name, lastName, age++);
        us.saveUser(name, lastName, age++);
        us.saveUser(name, lastName, age++);
        us.saveUser(name, lastName, age);

        us.getAllUsers().forEach(System.out::println);

        us.cleanUsersTable();

        us.dropUsersTable();
    }
}
