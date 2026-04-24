package models;

import models.phases.OpenPhase;

public class DevelopmentTicket {
    private String id;
    private String title;
    private TicketPhase phase;

    public DevelopmentTicket(String id, String title) {
        this.id = id;
        this.title = title;
        this.phase = new OpenPhase(); // Initial state
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPhaseName() {
        return phase.getName();
    }

    // Package-private or protected access so only phase classes can update it
    public void changePhase(TicketPhase newPhase) {
        this.phase = newPhase;
    }

    // Delegator methods
    public String startWork() {
        return phase.startWork(this);
    }

    public String sendToReview() {
        return phase.sendToReview(this);
    }

    public String approve() {
        return phase.approve(this);
    }

    public String reject() {
        return phase.reject(this);
    }

    public String cancel() {
        return phase.cancel(this);
    }

    @Override
    public String toString() {
        return "Ticket [" + id + "] " + title + " - State: " + getPhaseName();
    }
}
