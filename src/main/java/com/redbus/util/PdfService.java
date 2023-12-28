package com.redbus.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.redbus.user.payload.BookingsDetailsDto;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfService {

    public byte[] generatePdf(BookingsDetailsDto bookingsDetailsDto) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdfDocument = new PdfDocument(writer)) {

            try (Document document = new Document(pdfDocument)) {
                document.add(new Paragraph("Booking Details"));
                document.add(new Paragraph("Booking ID: " + bookingsDetailsDto.getBookingId()));
                document.add(new Paragraph("Ticket ID: " + bookingsDetailsDto.getTicketId()));
                document.add(new Paragraph("Bus Company: " + bookingsDetailsDto.getBusCompany()));
                document.add(new Paragraph("From: " + bookingsDetailsDto.getFrom()));
                document.add(new Paragraph("To: " + bookingsDetailsDto.getTo()));
                document.add(new Paragraph("First Name: " + bookingsDetailsDto.getFirstName()));
                document.add(new Paragraph("Last Name: " + bookingsDetailsDto.getLastName()));
                document.add(new Paragraph("Email: " + bookingsDetailsDto.getEmail()));
                document.add(new Paragraph("Mobile: " + bookingsDetailsDto.getMobile()));
                document.add(new Paragraph("Price: " + bookingsDetailsDto.getPrice()));
            }

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
