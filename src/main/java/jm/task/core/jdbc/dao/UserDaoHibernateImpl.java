package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Repository
@SuppressWarnings("deprecation") // for backwards compatibility
@Transactional(rollbackFor = SQLException.class) // for handling SQL exceptions in a transactional manner
public class UserDaoHibernateImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernateImpl.class);
    private final SessionFactory sessionFactory;

    @Autowired
    public UserDaoHibernateImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void createUsersTable() {
        logger.debug("Creating users table");
        try {
            Session session = sessionFactory.getCurrentSession();
            NativeQuery<?> query = session.createNativeQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id BIGINT PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(45)," +
                    "lastname VARCHAR(45)," +
                    "age TINYINT)");
            query.executeUpdate();
            logger.info("Users table created successfully.");
        } catch (RuntimeException e) {
            logger.error("Failed to create users table", e);
        }
    }

    @Override
    public void dropUsersTable() {
        logger.debug("Start dropping users table");
        try {
            Session session = sessionFactory.getCurrentSession();
            NativeQuery<?> query = session.createNativeQuery("DROP TABLE IF EXISTS users");
            query.executeUpdate();
            logger.info("Users table dropped successfully.");
        } catch (RuntimeException e) {
            logger.error("Error dropping Users table.", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        logger.debug("Start saving User");
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = new User(null, name, lastName, age);
            session.save(user);
            logger.info("User saved: {}", user);
        } catch (RuntimeException e) {
            logger.error("Error saving User", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        logger.debug("Start deleting User with id: {}", id);
        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                logger.info("User deleted with id: {}", id);
            }
        } catch (RuntimeException e) {
            logger.error("Error deleting User with id: {}", id, e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        logger.debug("Start retrieving all Users");
        try {
            Session session = sessionFactory.getCurrentSession();
            List<User> users = session.createQuery("from User", User.class).getResultList();
            logger.info("All users retrieved.");
            return users;
        } catch (RuntimeException e) {
            throw new DataRetrievalFailureException("Error with getAllUsers method", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        logger.debug("Start cleaning Users table");
        try {
            Session session = sessionFactory.getCurrentSession();

            // check table if exists
            List<?> result = session.createNativeQuery("SHOW TABLES LIKE 'users'").getResultList();
            if (!result.isEmpty()) {
                // if table exists, truncate
                session.createNativeQuery("TRUNCATE TABLE users").executeUpdate();
                logger.info("Users table cleaned successfully.");
            } else {
                logger.warn("Users table doesn't exist.");
            }
        } catch (RuntimeException e) {
            logger.error("Error cleaning Users table.", e);
        }
    }
}
