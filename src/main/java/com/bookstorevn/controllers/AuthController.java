package com.bookstorevn.controllers;

import com.bookstorevn.models.ApplicationUser;
import com.bookstorevn.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class AuthController {

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new ApplicationUser());
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") ApplicationUser user, RedirectAttributes redirectAttributes) {
        if (applicationUserRepository.findByEmail(user.getEmail()).isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng.");
            return "redirect:/register";
        }
        
        user.setId(UUID.randomUUID().toString());
        user.setRole("CUSTOMER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        applicationUserRepository.save(user);
        
        redirectAttributes.addFlashAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "redirect:/login";
    }
}
