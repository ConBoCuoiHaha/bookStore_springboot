package com.bookstorevn.controllers;

import com.bookstorevn.models.ApplicationUser;
import com.bookstorevn.models.OrderDetail;
import com.bookstorevn.models.OrderHeader;
import com.bookstorevn.repositories.ApplicationUserRepository;
import com.bookstorevn.repositories.OrderDetailRepository;
import com.bookstorevn.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/customer/order")
public class CustomerOrderController {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ApplicationUserRepository userRepository;

    private String getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) return null;
        ApplicationUser user = userRepository.findByEmail(auth.getName()).orElse(null);
        return user != null ? user.getId() : null;
    }

    @GetMapping("")
    public String index(Model model) {
        String userId = getUserId();
        if (userId == null) return "redirect:/login";

        List<OrderHeader> orderList = orderHeaderRepository.findByApplicationUserId(userId);
        model.addAttribute("orderList", orderList);
        return "customer/order/index";
    }

    @GetMapping("/details")
    public String details(@RequestParam("id") Integer id, Model model) {
        String userId = getUserId();
        if (userId == null) return "redirect:/login";

        OrderHeader orderHeader = orderHeaderRepository.findById(id).orElse(null);
        if (orderHeader == null || !orderHeader.getApplicationUser().getId().equals(userId)) {
            return "error/404";
        }

        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderHeaderId(id);
        model.addAttribute("orderHeader", orderHeader);
        model.addAttribute("orderDetails", orderDetails);
        return "customer/order/details";
    }
}
