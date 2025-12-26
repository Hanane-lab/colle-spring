package ma.ensaf.controller;

import ma.ensaf.model.Event;
import ma.ensaf.model.Ticket;
import ma.ensaf.model.User;
import ma.ensaf.repository.UserRepository;
import ma.ensaf.service.EventService;
import ma.ensaf.service.TicketService;
import ma.ensaf.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("hasRole('PARTICIPANT')")
    public ResponseEntity<?> bookTicket(@RequestParam Long eventId, @RequestParam Long userId) {
        try {
            Event event = eventService.getEventById(eventId);
            User participant = userService.findUserById(userId);

            Ticket ticket = ticketService.createTicket(event, participant);
            return ResponseEntity.ok(ticket);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthController.MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PARTICIPANT') or hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/qr/{qrCode}")
    @PreAuthorize("hasRole('PARTICIPANT') or hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<Ticket> getTicketByQrCode(@PathVariable String qrCode) {
        Ticket ticket = ticketService.getTicketByQrCode(qrCode);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/event/{eventId}")
    @PreAuthorize("hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<List<Ticket>> getTicketsByEvent(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        List<Ticket> tickets = ticketService.getTicketsByEvent(event);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/participant/{participantId}")
    @PreAuthorize("hasRole('PARTICIPANT') or hasRole('ADMIN')")
    public ResponseEntity<List<Ticket>> getTicketsByParticipant(@PathVariable Long participantId) {
        User participant = userService.findUserById(participantId);
        List<Ticket> tickets = ticketService.getTicketsByParticipant(participant);
        return ResponseEntity.ok(tickets);
    }

    @PutMapping("/{id}/invalidate")
    @PreAuthorize("hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<?> invalidateTicket(@PathVariable Long id) {
        try {
            ticketService.invalidateTicket(id);
            return ResponseEntity.ok(new AuthController.MessageResponse("Ticket invalidated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthController.MessageResponse(e.getMessage()));
        }
    }
}