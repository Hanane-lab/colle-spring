package ma.ensaf.service;

import ma.ensaf.model.Event;
import ma.ensaf.model.Ticket;
import ma.ensaf.model.User;
import ma.ensaf.repository.EventRepository;
import ma.ensaf.repository.TicketRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Event event, User participant) {
        if (ticketRepository.existsByEventAndParticipant(event, participant)) {
            throw new RuntimeException("Ticket already exists for this event and participant");
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