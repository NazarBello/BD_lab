package org.example;

import io.hypersistence.utils.hibernate.type.range.Range;
import org.example.builder.AccommodationBuilder;
import org.example.builder.BookingBuilder;
import org.example.builder.TripBuilder;
import org.example.builder.UserBuilder;
import org.example.entities.Accommodation;
import org.example.entities.Booking;
import org.example.entities.Trip;
import  org.example.entities.Users;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import jakarta.persistence.*;
import org.example.dao.*;

import java.time.LocalDate;
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




}
