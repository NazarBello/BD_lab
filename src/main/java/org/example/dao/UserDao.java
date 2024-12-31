package org.example.dao;


import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.example.entities.Users;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class UserDao {

    private final SessionFactory sessionFactory;

    // Pass in the SessionFactory (e.g. from HibernateUtil)
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void createUser(Users user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(user);
            tx.commit();
        }
        catch (HibernateException e) {
            // Log or print an error message here
            System.err.println("Error fetching user by ID " + user.getUsers_id() + ": " + e.getMessage());
        }
    }

    // READ by ID
    public Users findUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Users.class, id);
        }
        catch (HibernateException e) {
            // Log or print an error message here
            System.out.println("Error fetching user by ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    // READ all
    public List<Users> findAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Users", Users.class).list();
        }
        catch (HibernateException e) {
            System.out.println("Error fetching all users: " + e.getMessage());
            return null;
        }
    }

    // UPDATE
    public void updateUser(Users user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Input new name:");
            String name = scanner.nextLine();
            user.setUsers_name(name);
            session.merge(user);  // or session.update(user) if it's attached
            tx.commit();
        }
        catch (HibernateException e) {
            System.err.println("Error fetching user by ID " + user.getUsers_id() + ": " + e.getMessage());
        }
    }

    // DELETE
    public void deleteUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Users user = session.get(Users.class, id);
            if (user != null) {
                session.remove(user);
            }
            else{
                System.out.println("user not found");
            }
            tx.commit();
        }
        catch (HibernateException e) {
            System.err.println("Error fetching user by ID " + id + ": " + e.getMessage());
        }
    }
}



