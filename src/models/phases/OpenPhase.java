package models.phases;

import models.DevelopmentTicket;
import models.TicketPhase;

public class OpenPhase implements TicketPhase {
    @Override
    public String getName() {
        return "Open";
    }

    @Override
    public String startWork(DevelopmentTicket ticket) {
        ticket.changePhase(new DevelopmentPhase());
        return "Ticket transitioned to Development Phase (In Progress).";
    }

    @Override
    public String sendToReview(DevelopmentTicket ticket) {
        return "Error: An Open ticket cannot be sent to review.";
    }

    @Override
    public String approve(DevelopmentTicket ticket) {
        return "Error: An Open ticket cannot be approved.";
    }

    @Override
    public String reject(DevelopmentTicket ticket) {
        return "Error: An Open ticket cannot be rejected.";
    }

    @Override
    public String cancel(DevelopmentTicket ticket) {
        ticket.changePhase(new CancelledPhase());
        return "Ticket successfully cancelled.";
    }
}
