package ma.ensaf.controller;

import ma.ensaf.model.Role;
import ma.ensaf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping("/register/submit")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam String role,
            Model model) {

        // Validation
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Les mots de passe ne correspondent pas");
            return "register";
        }

        if (password.length() < 6) {
            model.addAttribute("error", "Le mot de passe doit contenir au moins 6 caractères");
            return "register";
        }

        try {
            // Créer le set de rôles
            Set<Role> roles = new HashSet<>();
            
            if ("ADMIN".equalsIgnoreCase(role)) {
                roles.add(Role.ADMIN);
            } else {
                roles.add(Role.PARTICIPANT);
            }

            // Créer l'utilisateur
            userService.createUser(username, password, roles);

            // Rediriger vers la page de connexion avec succès
            return "redirect:/login?registered=true";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}