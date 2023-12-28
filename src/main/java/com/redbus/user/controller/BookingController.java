package com.redbus.user.controller;

import com.redbus.user.payload.BookingsDetailsDto;
import com.redbus.user.payload.PassengerDetails;
import com.redbus.user.service.BookingService;
import com.redbus.util.EmailService;
import com.redbus.util.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;

    private EmailService emailService;

    private PdfService pdfService;

    public BookingController(BookingService bookingService, EmailService emailService, PdfService pdfService) {
        this.bookingService = bookingService;
        this.emailService = emailService;
        this.pdfService = pdfService;
    }

    // ... (other code)

    @PostMapping
    public ResponseEntity<BookingsDetailsDto> bookBus(
            @RequestParam("busId") String busId,
            @RequestParam("ticketId") String ticketId,
            @RequestBody PassengerDetails passengerDetails
    ) {
        BookingsDetailsDto booking = bookingService.createBooking(busId, ticketId, passengerDetails);

        if (booking != null){
            byte[] pdfBytes = pdfService.generatePdf(booking);

            sendBookingConfirmationEmailAttachment(passengerDetails, booking, pdfBytes);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    private void sendBookingConfirmationEmailAttachment(
            PassengerDetails passengerDetails, BookingsDetailsDto booking, byte[] pdfBytes){
        String emailSubject = "Booking Confirmed Id:" + booking.getBookingId();
        String emailBody = String.format("Your Booking is Confirmed\n Name:%s%s ", passengerDetails.getFirstName(), passengerDetails.getLastName());
        emailService.sendEmailWithAttachment(passengerDetails.getEmail(), emailSubject, emailBody, pdfBytes, "BookingConfirmation.pdf");
    }

}
