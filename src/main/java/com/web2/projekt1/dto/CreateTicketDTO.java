package com.web2.projekt1.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTicketDTO {
    @NotNull(message = "VATIN is required")
    private Long vatin;
    @NotNull(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    private String lastName;
}
