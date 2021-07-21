package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "id INTEGER not NULL AUTO_INCREMENT PRIMARY KEY," +
                " name VARCHAR(50)," +
                " lastName VARCHAR(50)," +
                " age INT(3))";
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        Session session = null;
    }

    @Override
    public void removeUserById(long id) {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        String hql = "from User";
        List<User> allUsers = new ArrayList<>();
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            allUsers = session.createQuery(hql, User.class).list();
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public void cleanUsersTable() {
        String hql = "delete User";
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery(hql);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }
}
