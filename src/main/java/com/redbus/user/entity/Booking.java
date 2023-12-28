package com.redbus.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "bookings")
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @Column(name = "bus_id")
    private String busId;

    @Column(name = "ticket_id")
    private String ticketId;

    @Column(name = "bus_company")
    private String busCompany;

    @Column(name = "to_city")
    private String toCity;

    @Column(name = "from_city")
    private String fromCity;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "price")
    private double price;


}
