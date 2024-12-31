package org.example.dao;

import org.example.builder.DateRangeBuilder;
import org.example.entities.Trip;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Scanner;

public class TripDao {

    private final SessionFactory sessionFactory;

    public TripDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTrip(Trip trip) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(trip);
            tx.commit();
        }
        catch (HibernateException e) {
            // Log or print an error message here
            System.err.println("Error fetching trip by ID " + trip.getTrip_id() + ": " + e.getMessage());
            // Optionally rethrow if you want the caller to handle it
        }
    }

    public Trip findTripById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Trip.class, id);
        }
        catch (HibernateException e) {
            System.err.println("Error fetching trip by ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    public List<Trip> findAllTrips() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Trip", Trip.class).list();
        }
        catch (HibernateException e) {
            System.err.println("Error fetching trip by ID " + e.getCause() + ": " + e.getMessage());
            return null;
        }
    }

    public void updateTrip(Trip trip) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter new title: ");
            String newTitle = scanner.nextLine();
            trip.setTitle(newTitle);
            trip.setTripPeriod(DateRangeBuilder.dateRangeBuilder());
            session.merge(trip);
            tx.commit();
        }
        catch (HibernateException e) {
            System.err.println("Error fetching trip by ID " + trip.getTrip_id() + ": " + e.getMessage());
        }
    }

    public void deleteTrip(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Trip trip = session.get(Trip.class, id);
            if (trip != null) {
                session.remove(trip);
            }
            else{
                System.out.println("Trip not found");
            }
            tx.commit();
        }
        catch (HibernateException e) {
            System.err.println("Error fetching trip by ID " + id + ": " + e.getMessage());
        }
    }
}