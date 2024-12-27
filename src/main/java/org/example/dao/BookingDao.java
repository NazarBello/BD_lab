package org.example.dao;

import org.example.entities.Booking;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class BookingDao {

    private final SessionFactory sessionFactory;

    public BookingDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createBooking(Booking booking) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(booking);
            tx.commit();
        }
        catch (HibernateException e ){
            System.err.println("Error fetching user by ID " + booking.getBookingId() + ": " + e.getMessage());
        }
    }

    public Booking findBookingById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Booking.class, id);
        }
        catch (HibernateException e ){
            System.err.println("Error fetching booking by ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    public List<Booking> findAllBookings() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Booking", Booking.class).list();
        }
        catch (HibernateException e ){
            System.err.println("Error fetching all bookings: " + e.getMessage());
            return null;
        }
    }

    public void updateBooking(Booking booking) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(booking);
            tx.commit();
        }
        catch (HibernateException e ){
            System.err.println("Error fetching booking by ID " + booking.getBookingId() + ": " + e.getMessage());
        }
    }

    public void deleteBooking(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            Booking booking = session.get(Booking.class, id);
            if (booking != null) {
                session.remove(booking);
            }
            else{
                System.out.println("Booking with ID " + id + " not found");
            }
            tx.commit();
        }
        catch (HibernateException e ){
            System.err.println("Error fetching booking by ID " + id + ": " + e.getMessage());
        }
    }
}
