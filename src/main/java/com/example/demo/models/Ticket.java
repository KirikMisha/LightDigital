package com.example.demo.models;

import com.example.demo.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
    private String userRequest;
    private String phoneNumber;
    private String ticketName;
}
