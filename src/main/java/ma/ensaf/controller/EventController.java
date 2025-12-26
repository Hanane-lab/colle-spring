package ma.ensaf.controller;

import ma.ensaf.model.Event;
import ma.ensaf.model.User;
import ma.ensaf.service.EventService;
import ma.ensaf.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String location) {

        List<Event> events = null;

        if (title != null && !title.isEmpty()) {
            events = eventService.searchEventsByTitle(title);
        } else if (location != null && !location.isEmpty()) {
            events = eventService.searchEventsByLocation(location);
        } else {
            events = eventService.getAllEvents();
        }

        return ResponseEntity.ok(events);
    }

    @PostMapping
    @PreAuthorize("hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<?> createEvent(@RequestBody Event event, @RequestParam Long organizerId) {
        try {
            User organizer = userService.findUserById(organizerId);
            event.setOrganizer(organizer);
            Event savedEvent = eventService.createEvent(event);
            return ResponseEntity.ok(savedEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthController.MessageResponse(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails) {
        try {
            Event updatedEvent = eventService.updateEvent(id, eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthController.MessageResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok(new AuthController.MessageResponse("Event deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthController.MessageResponse(e.getMessage()));
        }
    }

    @GetMapping("/organizer/{organizerId}")
    @PreAuthorize("hasRole('ORGANISATEUR') or hasRole('ADMIN')")
    public ResponseEntity<List<Event>> getEventsByOrganizer(@PathVariable Long organizerId) {
        User organizer = userService.findUserById(organizerId);
        List<Event> events = eventService.getEventsByOrganizer(organizer);
        return ResponseEntity.ok(events);
    }
}