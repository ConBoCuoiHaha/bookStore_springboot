package com.bookstorevn.config;

import com.bookstorevn.models.ApplicationUser;
import com.bookstorevn.repositories.ApplicationUserRepository;
import com.bookstorevn.repositories.ShoppingCartRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ApplicationUserRepository userRepository;

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            // Check if cart count is in session, if not, load from DB
            Object sessionCart = session.getAttribute("SessionCart");
            Integer count = null;
            
            if (sessionCart instanceof Long l) {
                count = l.intValue();
            } else if (sessionCart instanceof Integer i) {
                count = i;
            }

            if (count == null) {
                ApplicationUser user = userRepository.findByEmail(auth.getName()).orElse(null);
                if (user != null) {
                    count = shoppingCartRepository.countByApplicationUserId(user.getId()).intValue();
                    session.setAttribute("SessionCart", count);
                } else {
                    count = 0;
                }
            }
            model.addAttribute("cartCount", count);
        } else {
            model.addAttribute("cartCount", 0);
        }
    }
}
