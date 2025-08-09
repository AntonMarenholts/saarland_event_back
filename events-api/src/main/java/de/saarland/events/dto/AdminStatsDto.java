package de.saarland.events.dto;

public class AdminStatsDto {

    private long totalEvents;
    private long pendingEvents;
    private long approvedEvents;
    private long totalUsers;
    private long totalCategories;

    // Конструктор
    public AdminStatsDto(long totalEvents, long pendingEvents, long approvedEvents, long totalUsers, long totalCategories) {
        this.totalEvents = totalEvents;
        this.pendingEvents = pendingEvents;
        this.approvedEvents = approvedEvents;
        this.totalUsers = totalUsers;
        this.totalCategories = totalCategories;
    }

    // Геттеры и сеттеры
    public long getTotalEvents() { return totalEvents; }
    public void setTotalEvents(long totalEvents) { this.totalEvents = totalEvents; }
    public long getPendingEvents() { return pendingEvents; }
    public void setPendingEvents(long pendingEvents) { this.pendingEvents = pendingEvents; }
    public long getApprovedEvents() { return approvedEvents; }
    public void setApprovedEvents(long approvedEvents) { this.approvedEvents = approvedEvents; }
    public long getTotalUsers() { return totalUsers; }
    public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
    public long getTotalCategories() { return totalCategories; }
    public void setTotalCategories(long totalCategories) { this.totalCategories = totalCategories; }
}