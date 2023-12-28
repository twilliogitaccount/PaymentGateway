package com.redbus.user.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusListDto {
    private String busId;

    private String busNumber;

    private String busOperatorCompanyName;

    private int numberSeats;

    private String departureCity;

    private String arrivalCity;

    @JsonFormat(pattern = "HH:mm:ss")  // Specify the time format
    private LocalTime departureTime;

    @JsonFormat(pattern = "HH:mm:ss")  // Specify the time format
    private LocalTime arrivalTime;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Specify the date format
    private Date departureDate;

    @JsonFormat(pattern = "yyyy-MM-dd")  // Specify the date format
    private Date arrivalDate;

    private double totalTravelTime;

    private String busType;

    private String amenities;

}
