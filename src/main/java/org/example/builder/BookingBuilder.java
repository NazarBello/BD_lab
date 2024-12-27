package org.example.builder;

import org.example.DataBaseModel;
import org.example.entities.Booking;
import org.example.entities.Trip;
import org.example.entities.Users;
import org.hibernate.Session;

import java.util.Scanner;

public class BookingBuilder {

    public static Booking buildBooking(Session session) {

        Booking booking = new Booking();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter booking description:");
        booking.setBookingDescription(scanner.nextLine());
        System.out.println("Enter dooking date:");
        booking.setBookingPeriod(DateRangeBuilder.dateRangeBuilder());
        System.out.println("Enter booking status:");
        booking.setStatus(scanner.nextBoolean());
        System.out.println("Enter user id:");
        Long userId = scanner.nextLong();
        Users existingUser = session.get(Users.class, userId);
        if (existingUser != null) {
            booking.setUser(existingUser);
        }
        else{
            System.out.println("User not found");
            return null;
        }
        System.out.println("Enter trip id:");
        Long tripId = scanner.nextLong();
        Trip existingTrip = session.get(Trip.class, tripId);
        if (existingTrip != null) {
            booking.setTrip(existingTrip);
        }
        else{
            System.out.println("Trip not found");
            return null;
        }
        return booking;
    }
}
