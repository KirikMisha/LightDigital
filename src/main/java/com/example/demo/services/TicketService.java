package com.example.demo.services;

import com.example.demo.enums.TicketStatus;
import com.example.demo.models.Ticket;
import com.example.demo.models.User;
import com.example.demo.repositories.TicketRepository;
import com.example.demo.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, UserService userService){
        this.userService = userService;
        this.ticketRepository = ticketRepository;
    }

    public Ticket createTicket(Ticket ticket, String usernameFromToken) {
        Optional<User> userOptional = userService.findByUsername(usernameFromToken);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            ticket.setPhoneNumber(user.getPhoneNumber());
            ticket.setUserRequest(user.getUsername());
            ticket.setStatus(TicketStatus.DRAFT);
            return ticketRepository.save(ticket);
        } else {
            throw new UsernameNotFoundException("User not found: " + usernameFromToken);
        }
    }

    public List<Ticket> getAllTicketsSortedAndPaginated(int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);
        Page<Ticket> ticketPage = ticketRepository.findAll(pageable);
        return ticketPage.getContent();
    }

    public Optional<Ticket> getTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId);
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}

