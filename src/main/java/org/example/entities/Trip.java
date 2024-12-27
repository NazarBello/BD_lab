package org.example.entities;


import jakarta.persistence.*;
import io.hypersistence.utils.hibernate.type.range.Range;
import io.hypersistence.utils.hibernate.type.range.PostgreSQLRangeType;
import org.hibernate.annotations.Type;

import java.time.LocalDate;


@Entity
public class Trip {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "trip_id")
    private Long trip_id;


    @Column(name = "title" , nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "users_id" )
    private Users user;

    @Type(value = PostgreSQLRangeType.class)
    @Column(name = "trip_period" , columnDefinition = "daterange")
    private Range<LocalDate> tripPeriod;

    public Trip() {
    }

    public Trip(String title, Users user, Range<LocalDate> tripPeriod) {
        this.title = title;
        this.user = user;
        this.tripPeriod = tripPeriod;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Long trip_id) {
        this.trip_id = trip_id;
    }

    public Range<LocalDate> getTripPeriod() {
        return tripPeriod;
    }

    public void setTripPeriod(Range<LocalDate> tripPeriod) {
        this.tripPeriod = tripPeriod;
    }
}
