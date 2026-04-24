package services;

import models.DevelopmentTicket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketManagementService {
    private List<DevelopmentTicket> tickets;

    public TicketManagementService() {
        this.tickets = new ArrayList<>();
    }

    public void createTicket(String id, String title) {
        DevelopmentTicket newTicket = new DevelopmentTicket(id, title);
        tickets.add(newTicket);
    }

    public Optional<DevelopmentTicket> findTicketById(String id) {
        return tickets.stream()
            .filter(ticket -> ticket.getId().equals(id))
            .findFirst();
    }

    public List<DevelopmentTicket> getAllTickets() {
        return new ArrayList<>(tickets);
    }

    // Orchestrator methods using Optional
    
    public String startWorkOnTicket(String id) {
        return findTicketById(id)
                .map(DevelopmentTicket::startWork)
                .orElse("Error: No ticket found with ID " + id);
    }

    public String sendTicketToReview(String id) {
        return findTicketById(id)
                .map(DevelopmentTicket::sendToReview)
                .orElse("Error: No ticket found with ID " + id);
    }

    public String approveTicket(String id) {
        return findTicketById(id)
                .map(DevelopmentTicket::approve)
                .orElse("Error: No ticket found with ID " + id);
    }

    public String rejectTicket(String id) {
        return findTicketById(id)
                .map(DevelopmentTicket::reject)
                .orElse("Error: No ticket found with ID " + id);
    }

    public String cancelTicket(String id) {
        return findTicketById(id)
                .map(DevelopmentTicket::cancel)
                .orElse("Error: No ticket found with ID " + id);
    }
}
