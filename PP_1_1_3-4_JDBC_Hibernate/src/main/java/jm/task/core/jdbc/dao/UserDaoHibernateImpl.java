package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import javax.swing.*;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session = null;
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String sql = "CREATE TABLE IF NOT EXISTS tableuser " +
                "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                "age TINYINT NOT NULL)";
        try (Session session = Util.getSessionFactory().openSession();){
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS tableuser";
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e ){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name,lastName,age);
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().getCurrentSession();){
            transaction = session.beginTransaction();
            User userDB = session.get(User.class,id);
            session.remove(userDB);
            session.getTransaction().commit();
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try (Session session = Util.getSessionFactory().openSession()){
            users = (List<User>)Util.getSessionFactory().openSession().createQuery("From User tableuser").list();
        } catch (Exception e){
            //--------
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try (Session session = Util.getSessionFactory().openSession();){
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User tableuser").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
        }
    }
}
