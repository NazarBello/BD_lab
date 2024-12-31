package org.example.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "accomodation")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accom_id")
    private Long accommodationId;

    @Column(name = "address")
    private String address;

    @Column(name = "name")
    private String name;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "type")
    private String type;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    public Accommodation() {}

    public Accommodation(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
