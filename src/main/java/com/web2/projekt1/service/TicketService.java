package com.web2.projekt1.service;

import com.web2.projekt1.domain.Ticket;
import com.web2.projekt1.dto.CreateTicketDTO;
import com.web2.projekt1.repository.TicketRepo;
import com.web2.projekt1.util.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    @Value("${app.url}")
    private String appUrl;
    private final TicketRepo ticketRepo;

    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Integer getNumberOfTickets() { return ticketRepo.findAll().size(); }

    public byte[] createTicket(CreateTicketDTO ticketRequest) throws Exception {
        if (ticketRequest.getFirstName() == null || ticketRequest.getLastName() == null || ticketRequest.getVatin() == null) {
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "JSON does not contain all required data!");
        }
        if (ticketRepo.countAllByVatin(ticketRequest.getVatin()) >= 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximum number of tickets per VATIN is 3!");
        }
        Ticket ticket = Ticket.builder()
                .vatin(ticketRequest.getVatin())
                .firstName(ticketRequest.getFirstName())
                .lastName(ticketRequest.getLastName())
                .creationTime(new Timestamp(System.currentTimeMillis()))
                .build();
        Ticket createdTicket = ticketRepo.save(ticket);
        String url = appUrl + "/" + createdTicket.getId();

        BufferedImage qrCodeImage = QRCodeGenerator.generateQRCodeImage(url);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrCodeImage, "PNG", pngOutputStream);

        return pngOutputStream.toByteArray();

    }
    public Optional<Ticket> findById(UUID id) {
        return ticketRepo.findById(id);
    }

}
