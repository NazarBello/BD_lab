package org.example.builder;

import org.example.DataBaseModel;
import org.example.entities.Trip;
import org.example.entities.Users;
import org.hibernate.Session;

import java.util.Scanner;

public class TripBuilder {

    public static Trip buildTrip(Session session) {
        Trip trip = new Trip();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter trip title:");
        String tripTitle = scanner.nextLine();
        trip.setTitle(tripTitle);
        trip.setTripPeriod(DateRangeBuilder.dateRangeBuilder());
        System.out.println("Enter user id:");
        Long userId = scanner.nextLong();
        Users existingUser = session.get(Users.class, userId);
        trip.setUser(existingUser);
        return trip;
    }

}
