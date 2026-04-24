package models.phases;

import models.DevelopmentTicket;
import models.TicketPhase;

public class CancelledPhase implements TicketPhase {
    @Override
    public String getName() {
        return "Cancelled";
    }

    @Override
    public String startWork(DevelopmentTicket ticket) {
        return "Error: Ticket is cancelled. Cannot start work.";
    }

    @Override
    public String sendToReview(DevelopmentTicket ticket) {
        return "Error: Cancelled ticket cannot be reviewed.";
    }

    @Override
    public String approve(DevelopmentTicket ticket) {
        return "Error: Cancelled ticket cannot be approved.";
    }

    @Override
    public String reject(DevelopmentTicket ticket) {
        return "Error: Cancelled ticket cannot be rejected.";
    }

    @Override
    public String cancel(DevelopmentTicket ticket) {
        return "Error: Ticket is already cancelled.";
    }
}
