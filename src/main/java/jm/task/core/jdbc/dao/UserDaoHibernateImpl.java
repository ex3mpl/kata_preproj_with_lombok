package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

// method to connect from util class
import static jm.task.core.jdbc.util.Util.HibernateConnection;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
        // empty for Reflection API of Hibernate
    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null; // for if cycle in catch block
        try (Session session = HibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                    "name VARCHAR(45) NOT NULL," +
                    "lastname VARCHAR(45) NOT NULL," +
                    "age INTEGER(3) NULL)")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) { //  need if code have errors in transaction for good ending
                transaction.rollback();
            }
            throw new RuntimeException("error with createUsersTable method - ", e);
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("error with dropUsersTable method - ", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = HibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(null, name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("error with saveUser method - ", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = HibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.load(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("error with removeUserById method - ", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        try (Session session = HibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            List<User> users = session.createQuery("FROM User", User.class).list();
            transaction.commit();
            return users;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("error with getAllUsers method - ", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = HibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("error with cleanUsersTable method - ", e);
        }
    }

    // моя доработка для изучения Hibernate
    public List<User> getById(long id) {
        try (Session session = HibernateConnection().openSession()) {
            return Collections.singletonList(session.get(User.class, id));
        } catch (Exception e) {
            throw new RuntimeException("error with getById method - ", e);
        }
    }
}
