package models;

public interface TicketPhase {
    String getName();
    String startWork(DevelopmentTicket ticket);
    String sendToReview(DevelopmentTicket ticket);
    String approve(DevelopmentTicket ticket);
    String reject(DevelopmentTicket ticket);
    String cancel(DevelopmentTicket ticket);
}
