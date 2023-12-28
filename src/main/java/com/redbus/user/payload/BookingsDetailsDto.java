package com.redbus.user.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingsDetailsDto {
    private String bookingId;
    private String ticketId;
    private String busCompany;
    private String to;
    private String from;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private double price;
    private String message;

}
