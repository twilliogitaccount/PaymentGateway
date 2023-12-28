package com.redbus.user.service;

import com.redbus.operator.entity.BusOperator;
import com.redbus.operator.entity.TicketCost;
import com.redbus.operator.repository.BusOperatorRepository;
import com.redbus.user.entity.Booking;
import com.redbus.user.payload.BookingsDetailsDto;
import com.redbus.user.payload.PassengerDetails;
import com.redbus.user.repository.BookingRepository;
import com.redbus.user.repository.TicketCostRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private BusOperatorRepository busOperatorRepository;

    private TicketCostRepository ticketCostRepository;

    private BookingRepository bookingRepository;

    public BookingService(BusOperatorRepository busOperatorRepository, TicketCostRepository ticketCostRepository, BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }

    public BookingsDetailsDto createBooking(
            String busId,
            String ticketId,
            PassengerDetails passengerDetails
    ){
        BusOperator bus = busOperatorRepository.findById(busId).get();
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).get();

        String paymentIntent = createPaymentIntent((int) ticketCost.getCost());

        if (paymentIntent!=null) {

            Booking booking = new Booking();
            String bookingId = UUID.randomUUID().toString();
            booking.setBusId(busId);
            booking.setTicketId(ticketId);
            booking.setBookingId(bookingId);
            booking.setToCity(bus.getArrivalCity());
            booking.setFromCity(bus.getDepartureCity());
            booking.setBusCompany(bus.getBusOperatorCompanyName());
            booking.setPrice(ticketCost.getCost());
            booking.setFirstName(passengerDetails.getFirstName());
            booking.setLastName(passengerDetails.getLastName());
            booking.setEmail(passengerDetails.getEmail());
            booking.setMobile(passengerDetails.getMobile());

            Booking ticketCreatedDetails = bookingRepository.save(booking);

            BookingsDetailsDto dto = new BookingsDetailsDto();
            dto.setBookingId(ticketCreatedDetails.getBookingId());
            dto.setTicketId(ticketCreatedDetails.getTicketId());
            dto.setFirstName(ticketCreatedDetails.getFirstName());
            dto.setLastName(ticketCreatedDetails.getLastName());
            dto.setTo(ticketCreatedDetails.getToCity());
            dto.setFrom(ticketCreatedDetails.getFromCity());
            dto.setPrice(ticketCreatedDetails.getPrice());
            dto.setEmail(ticketCreatedDetails.getEmail());
            dto.setMobile(ticketCreatedDetails.getMobile());
            dto.setBusCompany(ticketCreatedDetails.getBusCompany());
            dto.setMessage("Booking Confirmed");

            return dto;

        }else {
            System.out.println("Error!!");
        }
        return null;


    }

    public String createPaymentIntent(Integer amount) {
        Stripe.apiKey = stripeApiKey;
        try {
            PaymentIntent intent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency("usd")
                            .setAmount((long) amount * 100) // Amount in cents
                            .build()
            );
            return generateResponse(intent.getClientSecret());
        } catch (StripeException e) {
            return generateResponse("Error creating PaymentIntent:" + e.getMessage());
        }
    }

    private String generateResponse(String clientSecret) {
        return "{\"clientSecret\":\"" + clientSecret + "\"}";
    }
}
