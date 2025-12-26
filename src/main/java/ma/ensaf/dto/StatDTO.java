package ma.ensaf.dto;

import java.util.Map;

public class StatDTO {
    private Long totalEvents;
    private Long totalTicketsSold;
    private Long totalUsers;
    private Double totalRevenue;
    private Map<String, Long> categoryStats;
    private Map<String, Long> salesStats;

    // Constructeurs
    public StatDTO() {}

    public StatDTO(Long totalEvents, Long totalTicketsSold, Long totalUsers, Double totalRevenue,
                   Map<String, Long> categoryStats, Map<String, Long> salesStats) {
        this.totalEvents = totalEvents;
        this.totalTicketsSold = totalTicketsSold;
        this.totalUsers = totalUsers;
        this.totalRevenue = totalRevenue;
        this.categoryStats = categoryStats;
        this.salesStats = salesStats;
    }

    // Getters et Setters
    public Long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(Long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Long getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(Long totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Map<String, Long> getCategoryStats() {
        return categoryStats;
    }

    public void setCategoryStats(Map<String, Long> categoryStats) {
        this.categoryStats = categoryStats;
    }

    public Map<String, Long> getSalesStats() {
        return salesStats;
    }

    public void setSalesStats(Map<String, Long> salesStats) {
        this.salesStats = salesStats;
    }
}