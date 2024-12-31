package org.example;

import org.example.builder.*;
import org.example.entities.*;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.example.dao.*;
import java.util.Scanner;

public class DataBaseModel {
    private final UserDao userDao;
    private final TripDao tripDao;
    private final BookingDao bookingDao;
    private final AccommodationDao accommodationDao;
    // etc.

    public DataBaseModel(SessionFactory sessionFactory) {
        this.userDao = new UserDao(sessionFactory);
        this.tripDao = new TripDao(sessionFactory);
        this.bookingDao = new BookingDao(sessionFactory);
        this.accommodationDao = new AccommodationDao(sessionFactory);
    }

    public void insert() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter entity name to insert (e.g. 'users', 'trip'):");
            String choice = sc.nextLine().toLowerCase();

            switch (choice) {
                case "users":
                    Users user = UserBuilder.buildUser();;
                    userDao.createUser(user);
                    System.out.println("Inserted new User with name = " + user.getUsers_name());
                    break;
                case "trip":
                    Trip trip = TripBuilder.buildTrip(session);
                    tripDao.createTrip(trip);
                    System.out.println("Inserted new Trip with id = " + trip.getTrip_id());
                    break;
                case "booking":
                    Booking booking = BookingBuilder.buildBooking(session);
                    bookingDao.createBooking(booking);
                    System.out.println("Inserted new Booking with id = " + booking.getBookingId());
                    break;
                case "accommodation":
                    Accommodation accommodation = AccommodationBuilder.buildAccommodation(session);
                    accommodationDao.createAccommodation(accommodation);
                    System.out.println("Inserted new Accommodation with id = " + accommodation.getAccommodationId() );
                default:
                    System.out.println("Unknown entity: " + choice);
                    break;
            }

            tx.commit();
        }
    }

    public void delete(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            Scanner sc = new Scanner(System.in);
            System.out.println("Enter entity name to delete (e.g. 'users', 'trip'):");
            String choice = sc.nextLine().toLowerCase();
            switch (choice) {
                case "users":
                    System.out.println("Enter user id to delete:");
                    Long id = sc.nextLong();
                    userDao.deleteUser(id);
                    break;
                case "trip":
                    System.out.println("Enter trip id to delete:");
                    Long tripId = sc.nextLong();
                    tripDao.deleteTrip(tripId);
                    break;
                case "booking":
                    System.out.println("Enter booking id to delete:");
                    Long bookingId = sc.nextLong();
                    bookingDao.deleteBooking(bookingId);
                    break;
                case "accommodation":
                    System.out.println("Enter accommodation id to delete:");
                    Long accommodationId = sc.nextLong();
                    accommodationDao.deleteAccommodation(accommodationId);
                    break;
                default:
                    System.out.println("Unknown entity: " + choice);
            }
        }
    }

    public void update(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter entity name to update (e.g. 'users', 'trip'):");
            String choice = sc.nextLine().toLowerCase();
            switch (choice) {
                case "users":
                    System.out.println("Enter user id to update:");
                    Long id = sc.nextLong();
                    Users user = session.load(Users.class, id);
                    userDao.updateUser(user);
                    break;
                case "trip":
                    System.out.println("Enter trip id to update:");
                    Long tripId = sc.nextLong();
                    Trip trip = session.load(Trip.class, tripId);
                    tripDao.updateTrip(trip);
                    break;
                case "booking":
                    System.out.println("Enter booking id to update:");
                    Long bookingId = sc.nextLong();
                    Booking booking = session.load(Booking.class, bookingId);
                    bookingDao.updateBooking(booking);
                    break;
                case "accommodation":
                    System.out.println("Enter accommodation id to update:");
                    Long accommodationId = sc.nextLong();
                    Accommodation accommodation = session.load(Accommodation.class, accommodationId);
                    accommodationDao.updateAccommodation(accommodation);
                    break;
                    default:
                        System.out.println("Unknown entity: " + choice);
                        break;
            }
        }
    }




}
