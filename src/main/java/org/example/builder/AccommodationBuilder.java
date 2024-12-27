package org.example.builder;

import org.example.entities.Accommodation;
import org.example.entities.Booking;
import org.hibernate.Session;

import java.util.Scanner;

public class AccommodationBuilder {

    public static Accommodation buildAccommodation(Session session) {
        Accommodation accommodation = new Accommodation();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the accommodation: ");
        accommodation.setName(scanner.nextLine());
        System.out.println("Enter type of accommodation: ");
        accommodation.setType(scanner.nextLine());
        System.out.println("Enter address of accommodation: ");
        accommodation.setAddress(scanner.nextLine());
        System.out.println("Enter booking id: ");
        Long bookingId = scanner.nextLong();
        Booking booking = session.get(Booking.class, bookingId);
        if (booking == null) {
            accommodation.setBooking(booking);

        }
        else {
            System.out.println("Booking does not exist");
            return null;
        }
        return accommodation;
    }
}
