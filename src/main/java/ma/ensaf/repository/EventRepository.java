package ma.ensaf.repository;


import ma.ensaf.model.Event;
import ma.ensaf.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByOrganizer(User organizer);
    List<Event> findByEventDateAfter(LocalDateTime date);
    List<Event> findByLocationContainingIgnoreCase(String location);
    List<Event> findByTitleContainingIgnoreCase(String title);
}