package org.example.dao;

import org.example.entities.Accommodation;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class AccommodationDao {

    private final SessionFactory sessionFactory;

    public AccommodationDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createAccommodation(Accommodation accommodation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(accommodation);
            tx.commit();
        }
        catch (HibernateException e){
            System.err.println("Error fetching accommodation by ID " + accommodation.getAccommodationId() + ": " + e.getMessage());
            // Optionally rethrow if you want the caller to handle it
            throw e;
        }
    }

    public Accommodation findAccommodationById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Accommodation.class, id);
        }
        catch (HibernateException e){
            System.err.println("Error fetching accommodation by ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    public List<Accommodation> findAllAccommodations() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Accommodation", Accommodation.class).list();
        }
        catch (HibernateException e){
            System.err.println("Error fetching all accommodations: " + e.getMessage());
            return null;
        }
    }

    public void updateAccommodation(Accommodation accomodation) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter accommodation name: ");
            accomodation.setName(scanner.nextLine());
            System.out.println("Enter accommodation type: ");
            accomodation.setType(scanner.nextLine());
            System.out.println("Enter accommodation address: ");
            accomodation.setAddress(scanner.nextLine());
            session.merge(accomodation);
            tx.commit();
        }
        catch (HibernateException e){
            System.err.println("Error updating accommodation: " + e.getMessage());
        }
    }

    public void deleteAccommodation(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Accommodation accommodation = session.get(Accommodation.class, id);
            if (accommodation != null) {
                session.remove(accommodation);
            }
            else{
                System.out.println("Accommodation with ID " + id + " not found");
            }
            tx.commit();
        }
        catch (HibernateException e){
            System.err.println("Error deleting accommodation: " + e.getMessage());
        }
    }
}
