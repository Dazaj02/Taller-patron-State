package models;

import models.phases.OpenPhase;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DevelopmentTicket {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private String assignee;
    private List<String> auditLogs;
    private TicketPhase phase;

    public DevelopmentTicket(String id, String title, String description, Priority priority, String assignee) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.assignee = assignee;
        this.auditLogs = new ArrayList<>();
        this.phase = new OpenPhase(); // Initial state
        addLog("Ticket created in Open phase.");
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getAssignee() {
        return assignee;
    }

    public List<String> getAuditLogs() {
        return new ArrayList<>(auditLogs);
    }

    public String getPhaseName() {
        return phase.getName();
    }

    public void addLog(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        auditLogs.add("[" + dtf.format(now) + "] " + message);
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
        return "Ticket [" + id + "] " + title + " - State: " + getPhaseName() + " (Priority: " + priority + ")";
    }
}
