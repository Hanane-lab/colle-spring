package ma.ensaf.controller;

import ma.ensaf.model.Event;
import ma.ensaf.model.Ticket;
import ma.ensaf.model.User;
import ma.ensaf.service.EventService;
import ma.ensaf.service.TicketService;
import ma.ensaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("currentUser", user);
        }
        
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<Ticket> tickets = ticketService.getTicketsByParticipant(user);

        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        model.addAttribute("events", eventService.getAllEvents());
        return "user/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model, Authentication authentication) {
        List<Event> events = eventService.getAllEvents();
        model.addAttribute("events", events);
        
        // Statistiques
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalEvents", events.size());
        stats.put("totalTickets", ticketService.getAllTickets().size());
        stats.put("totalUsers", userService.getAllUsers().size());
        
        model.addAttribute("stats", stats);
        return "admin/dashboard";
    }

    @PostMapping("/user/tickets/book")
    public String bookTicket(@RequestParam Long eventId, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Event event = eventService.getEventById(eventId);
        
        try {
            ticketService.createTicket(event, user);
            return "redirect:/user/dashboard?success=true";
        } catch (Exception e) {
            return "redirect:/user/dashboard?error=" + e.getMessage();
        }
    }

    @PostMapping("/admin/events/add")
    public String addEvent(@ModelAttribute Event event, Authentication authentication) {
        String username = authentication.getName();
        User organizer = userService.findByUsername(username);
        event.setOrganizer(organizer);
        eventService.createEvent(event);
        return "redirect:/admin/dashboard?success=true";
    }

    @PostMapping("/admin/events/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/dashboard?deleted=true";
    }
}