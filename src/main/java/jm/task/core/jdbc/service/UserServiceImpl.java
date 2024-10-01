package jm.task.core.jdbc.service;

// import jm.task.core.jdbc.dao.UserDaoJDBCImpl;    // for 1.1.3 task with unused SQLExceptions
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;  // for 1.1.4 task with Hibernate
import jm.task.core.jdbc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserDaoHibernateImpl userDao;

    @Autowired
    public UserServiceImpl(UserDaoHibernateImpl userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUsersTable() throws SQLException {
        userDao.createUsersTable();
    }

    @Override
    public void dropUsersTable() throws SQLException {
        userDao.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) throws SQLException {
        userDao.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) throws SQLException {
        userDao.removeUserById(id);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws SQLException {
        return userDao.getAllUsers();
    }

    @Override
    public void cleanUsersTable() throws SQLException {
        userDao.cleanUsersTable();
    }
}
