package ma.ensaf.service;

import ma.ensaf.model.Payment;
import ma.ensaf.model.Ticket;
import ma.ensaf.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Ticket ticket, Double amount) {
        // Simulate payment processing
        String transactionId = "TXN_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Payment payment = new Payment(ticket, amount, transactionId);

        // In a real application, we would integrate with a payment gateway here
        // For simulation purposes, we'll consider the payment successful
        payment.setStatus(Payment.PaymentStatus.COMPLETED);

        return paymentRepository.save(payment);
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public List<Payment> getPaymentsByTicket(Ticket ticket) {
        return paymentRepository.findByTicket(ticket);
    }

    public List<Payment> getPaymentsByStatus(Payment.PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    public void updatePaymentStatus(Long id, Payment.PaymentStatus status) {
        Payment payment = getPaymentById(id);
        payment.setStatus(status);
        paymentRepository.save(payment);
    }
}