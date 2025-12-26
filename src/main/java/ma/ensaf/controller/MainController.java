package ma.ensaf.controller;


import ma.ensaf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
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

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, @RequestParam String confirmPassword, 
                              @RequestParam String role, Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas");
            return "register";
        }

        User existingUser = userService.findByEmail(user.getEmail());
        if (existingUser != null) {
            model.addAttribute("error", "Un utilisateur avec cet email existe déjà");
            return "register";
        }

        user.setRole("ROLE_" + role);
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByEmail(username);
        model.addAttribute("user", user);
        model.addAttribute("userForm", user);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute User userForm, 
                               @RequestParam(required = false) String currentPassword,
                               @RequestParam(required = false) String newPassword,
                               @RequestParam(required = false) String confirmNewPassword,
                               Authentication authentication, Model model) {
        String username = authentication.getName();
        User user = userService.findByEmail(username);

        if (currentPassword != null && !currentPassword.isEmpty()) {
            if (!userService.checkPassword(user, currentPassword)) {
                model.addAttribute("error", "Mot de passe actuel incorrect");
                return "profile";
            }

            if (newPassword == null || !newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "Les nouveaux mots de passe ne correspondent pas");
                return "profile";
            }

            user.setPassword(userService.encodePassword(newPassword));
        }

        user.setUsername(userForm.getUsername());
        user.setEmail(userForm.getEmail());
        userService.save(user);

        return "redirect:/profile";
    }

    @GetMapping("/user")
    public String userDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userService.findByEmail(username);
        List<Ticket> tickets = ticketService.findByUserEmail(username);

        model.addAttribute("user", user);
        model.addAttribute("tickets", tickets);
        return "user/dashboard";
    }

    @GetMapping("/admin")
    public String adminDashboard(Model model, Authentication authentication) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        
        // Ajouter les statistiques
        model.addAttribute("stats", eventService.getStatistics());
        return "admin/dashboard";
    }

    @GetMapping("/admin/stats")
    public String adminStats(Model model, Authentication authentication) {
        List<Event> events = eventService.findAll();
        model.addAttribute("events", events);
        model.addAttribute("stats", eventService.getStatistics());
        return "admin/stats";
    }

    @PostMapping("/admin/events")
    public String addEvent(@ModelAttribute Event event) {
        eventService.save(event);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/events/{id}")
    @ResponseBody
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteById(id);
        return "OK";
    }

    @PostMapping("/api/tickets/purchase/{eventId}")
    @ResponseBody
    public String purchaseTicket(@PathVariable Long eventId, Authentication authentication) {
        String userEmail = authentication.getName();
        try {
            ticketService.purchaseTicket(eventId, userEmail);
            return "{\"success\": true, \"message\": \"Ticket acheté avec succès\"}";
        } catch (Exception e) {
            return "{\"success\": false, \"message\": \"" + e.getMessage() + "\"}";
        }
    }
}