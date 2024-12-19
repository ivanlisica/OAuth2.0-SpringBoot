package com.web2.projekt1.controller;

import com.web2.projekt1.domain.Ticket;
import com.web2.projekt1.dto.CreateTicketDTO;
import com.web2.projekt1.service.TicketService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Controller
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("numberOfTickets", ticketService.getNumberOfTickets());
        return "homePage";
    }

    @PostMapping("/createTicket")
    public ResponseEntity<?> createTicket(@RequestBody CreateTicketDTO ticketRequest) {
        try {
            byte[] qrCodeBytes = ticketService.createTicket(ticketRequest);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            return ResponseEntity.ok().headers(headers).body(qrCodeBytes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }
    @GetMapping("/{id}")
    public String getTicketDetails(Model model, @PathVariable UUID id, @AuthenticationPrincipal OidcUser principal) {
        Optional<Ticket> ticket = ticketService.findById(id);
        model.addAttribute("name", principal.getFullName());
        assert ticket.orElse(null) != null;
        model.addAttribute("ticket", ticket.orElse(null));
        return "ticketDetails";
    }


}
