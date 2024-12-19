package com.web2.projekt1.repository;

import com.web2.projekt1.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TicketRepo extends JpaRepository<Ticket, UUID> {
    Integer countAllByVatin(Long vatin);
}
