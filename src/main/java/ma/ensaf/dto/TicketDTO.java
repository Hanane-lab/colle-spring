package ma.ensaf.dto;

import java.time.LocalDateTime;
import ma.ensaf.dto.EventDTO;

public class TicketDTO {
    private Long id;
    private EventDTO event;
    private LocalDateTime purchaseDate;
    private String qrCode;

    // Constructeurs
    public TicketDTO() {}

    public TicketDTO(Long id, EventDTO event, LocalDateTime purchaseDate, String qrCode) {
        this.id = id;
        this.event = event;
        this.purchaseDate = purchaseDate;
        this.qrCode = qrCode;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EventDTO getEvent() {
        return event;
    }

    public void setEvent(EventDTO event) {
        this.event = event;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}