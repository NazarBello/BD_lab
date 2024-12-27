package org.example.entities;


import io.hypersistence.utils.hibernate.type.range.PostgreSQLRangeType;
import io.hypersistence.utils.hibernate.type.range.Range;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "booking")
public class Booking {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;


    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    private boolean status;

    @Type(PostgreSQLRangeType.class)
    @Column(name = "booking_period" , columnDefinition = "daterange")
    private Range<LocalDate> bookingPeriod;

    @Column(name = "booking_desc")
    private String bookingDescription;


    @OneToOne
    @JoinColumn(name = "accom_id")
    private Accommodation accommodation;


    public Booking() {
    }

    public Booking(Long bookingId, Users user) {
        this.bookingId = bookingId;
        this.user = user;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Range<LocalDate> getBookingPeriod() {
        return bookingPeriod;
    }

    public void setBookingPeriod(Range<LocalDate> bookingPeriod) {
        this.bookingPeriod = bookingPeriod;
    }

    public String getBookingDescription() {
        return bookingDescription;
    }

    public void setBookingDescription(String bookingDescription) {
        this.bookingDescription = bookingDescription;
    }

    public Accommodation getAccomodation() {
        return accommodation;
    }

    public void setAccomodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }
}
