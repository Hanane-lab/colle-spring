package ma.ensaf.dto;

import java.time.LocalDateTime;

public class EventDTO {
    private Long id;
    private String name;
    private LocalDateTime date;
    private String location;
    private String category;
    private Double price;
    private Integer ticketsSold;

    // Constructeurs
    public EventDTO() {}

    public EventDTO(Long id, String name, LocalDateTime date, String location, String category, Double price) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.category = category;
        this.price = price;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(Integer ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}