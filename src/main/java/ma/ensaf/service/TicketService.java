package ma.ensaf.service;

import ma.ensaf.model.Event;
import ma.ensaf.model.Ticket;
import ma.ensaf.model.User;
import ma.ensaf.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Event event, User participant) {
        if (ticketRepository.existsByEventAndParticipant(event, participant)) {
            throw new RuntimeException("Vous avez déjà réservé un ticket pour cet événement");
        }

        Ticket ticket = new Ticket(event, participant);
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id: " + id));
    }

    public Ticket getTicketByQrCode(String qrCode) {
        return ticketRepository.findByQrCode(qrCode)
                .orElseThrow(() -> new RuntimeException("Ticket not found with QR code: " + qrCode));
    }

    public List<Ticket> getTicketsByEvent(Event event) {
        return ticketRepository.findByEvent(event);
    }

    public List<Ticket> getTicketsByParticipant(User participant) {
        return ticketRepository.findByParticipant(participant);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void invalidateTicket(Long id) {
        Ticket ticket = getTicketById(id);
        ticket.setValid(false);
        ticketRepository.save(ticket);
    }

    public void validateTicket(String qrCode) {
        Ticket ticket = getTicketByQrCode(qrCode);
        if (!ticket.getValid()) {
            throw new RuntimeException("Ticket is already invalid");
        }
        ticket.setValid(true);
        ticketRepository.save(ticket);
    }
}