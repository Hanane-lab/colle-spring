package ma.ensaf.repository;

import ma.ensaf.model.Payment;
import ma.ensaf.model.Ticket;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByTicket(Ticket ticket);
    List<Payment> findByStatus(Payment.PaymentStatus status);
}