package org.example.entities;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long users_id;

    @Column(name = "name")
    private String users_name;

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Trip> trips;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public Users() {
    }

    public Users(Long users_id, String users_name) {
        this.users_id = users_id;
        this.users_name = users_name;
    }

    public Users(String users_name) {
        this.users_name = users_name;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Long getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Long users_id) {
        this.users_id = users_id;
    }

    public String getUsers_name() {
        return users_name;
    }

    public void setUsers_name(String usersName) {
        this.users_name = usersName;
    }
}
