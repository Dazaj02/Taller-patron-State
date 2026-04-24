package models.phases;

import models.DevelopmentTicket;
import models.TicketPhase;

public class DevelopmentPhase implements TicketPhase {
    @Override
    public String getName() {
        return "In Progress";
    }

    @Override
    public String startWork(DevelopmentTicket ticket) {
        return "Error: Ticket is already in progress.";
    }

    @Override
    public String sendToReview(DevelopmentTicket ticket) {
        ticket.changePhase(new ReviewPhase());
        ticket.addLog("Ticket sent to code review (Under Review).");
        return "Ticket sent to code review (Under Review).";
    }

    @Override
    public String approve(DevelopmentTicket ticket) {
        return "Error: An In Progress ticket cannot be approved without review.";
    }

    @Override
    public String reject(DevelopmentTicket ticket) {
        return "Error: An In Progress ticket cannot be directly rejected.";
    }

    @Override
    public String cancel(DevelopmentTicket ticket) {
        ticket.changePhase(new CancelledPhase());
        ticket.addLog("The development ticket has been cancelled.");
        return "The development ticket has been cancelled.";
    }
}
