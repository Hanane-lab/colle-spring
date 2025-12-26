package ma.ensaf.controller;

import ma.ensaf.model.Payment;
import ma.ensaf.model.Ticket;
import ma.ensaf.service.PaymentService;
import ma.ensaf.service.TicketService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @PreAuthorize("hasRole('PARTICIPANT')")
    public ResponseEntity<?> processPayment(@RequestParam Long ticketId, @RequestParam Double amount) {
        try {
            Ticket ticket = ticketService.getTicketById(ticketId);
            Payment payment = paymentService.createPayment(ticket, amount);
            return ResponseEntity.ok(payment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthController.MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PARTICIPANT') or hasRole('ADMIN')")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(payment);
    }

    @GetMapping("/ticket/{ticketId}")
    @PreAuthorize("hasRole('PARTICIPANT') or hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> getPaymentsByTicket(@PathVariable Long ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        List<Payment> payments = paymentService.getPaymentsByTicket(ticket);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Payment>> getPaymentsByStatus(@PathVariable Payment.PaymentStatus status) {
        List<Payment> payments = paymentService.getPaymentsByStatus(status);
        return ResponseEntity.ok(payments);
    }
}