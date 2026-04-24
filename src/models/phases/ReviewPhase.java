package models.phases;

import models.DevelopmentTicket;
import models.TicketPhase;

public class ReviewPhase implements TicketPhase {
    @Override
    public String getName() {
        return "Under Review";
    }

    @Override
    public String startWork(DevelopmentTicket ticket) {
        return "Error: Ticket is under review, development cannot be restarted directly.";
    }

    @Override
    public String sendToReview(DevelopmentTicket ticket) {
        return "Error: Ticket is already under review.";
    }

    @Override
    public String approve(DevelopmentTicket ticket) {
        ticket.changePhase(new CompletedPhase());
        return "Ticket approved and marked as Completed.";
    }

    @Override
    public String reject(DevelopmentTicket ticket) {
        ticket.changePhase(new DevelopmentPhase());
        return "Ticket rejected and returned to Development (In Progress).";
    }

    @Override
    public String cancel(DevelopmentTicket ticket) {
        ticket.changePhase(new CancelledPhase());
        return "The ticket under review has been cancelled.";
    }
}
