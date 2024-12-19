package com.web2.projekt1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.UuidGenerator;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ticket")
public class Ticket {

    @Id
    @NotNull
    @UuidGenerator
    private UUID id;

    @NotNull
    private Long vatin;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Timestamp creationTime;



}
