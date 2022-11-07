package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDao USD = new UserDaoHibernateImpl();
    @Override
    public void createUsersTable() throws SQLException {
        USD.createUsersTable();
    }

    public void dropUsersTable() {
        USD.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        USD.saveUser(name,lastName,age);
    }

    public void removeUserById(long id) {
        USD.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return USD.getAllUsers();
    }

    public void cleanUsersTable() {
        USD.cleanUsersTable();
    }
}
