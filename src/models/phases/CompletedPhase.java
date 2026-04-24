package models.phases;

import models.DevelopmentTicket;
import models.TicketPhase;

public class CompletedPhase implements TicketPhase {
    @Override
    public String getName() {
        return "Completed";
    }

    @Override
    public String startWork(DevelopmentTicket ticket) {
        return "Error: Ticket is already completed. Cannot start work.";
    }

    @Override
    public String sendToReview(DevelopmentTicket ticket) {
        return "Error: Ticket is already completed. Cannot send to review.";
    }

    @Override
    public String approve(DevelopmentTicket ticket) {
        return "Error: Ticket is already completed and was previously approved.";
    }

    @Override
    public String reject(DevelopmentTicket ticket) {
        return "Error: Ticket is already completed. Cannot reject.";
    }

    @Override
    public String cancel(DevelopmentTicket ticket) {
        return "Error: Ticket is already completed. Cannot cancel finished work.";
    }
}
