package controllers;

import models.DevelopmentTicket;
import services.TicketManagementService;

import java.util.List;

public class TicketController {
    private TicketManagementService service;

    public TicketController() {
        this.service = new TicketManagementService();
    }

    public String createTicket(String id, String title) {
        if (id == null || id.trim().isEmpty() || title == null || title.trim().isEmpty()) {
            return "Error: ID and title cannot be empty.";
        }
        if (service.findTicketById(id).isPresent()) {
            return "Error: A ticket already exists with ID " + id;
        }
        service.createTicket(id, title);
        return "Ticket created successfully: [" + id + "] " + title;
    }

    public List<DevelopmentTicket> getAllTickets() {
        return service.getAllTickets();
    }

    public String startWork(String id) {
        return service.startWorkOnTicket(id);
    }

    public String sendToReview(String id) {
        return service.sendTicketToReview(id);
    }

    public String approve(String id) {
        return service.approveTicket(id);
    }

    public String reject(String id) {
        return service.rejectTicket(id);
    }

    public String cancel(String id) {
        return service.cancelTicket(id);
    }
}
