package ma.ensaf.repository;


import ma.ensaf.model.Event;
import  ma.ensaf.model.Ticket;
import ma.ensaf.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByEvent(Event event);
    List<Ticket> findByParticipant(User participant);
    Optional<Ticket> findByQrCode(String qrCode);
    boolean existsByEventAndParticipant(Event event, User participant);
}