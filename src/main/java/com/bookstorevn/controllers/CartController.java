package com.bookstorevn.controllers;

import com.bookstorevn.models.*;
import com.bookstorevn.models.ViewModels.ShoppingCartVM;
import com.bookstorevn.repositories.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customer/cart")
public class CartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private String getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Simplified: assuming username is used as ID or we find by email
        // In a real app with Identity migration, we'd use the stored GUID string
        ApplicationUser user = applicationUserRepository.findByEmail(auth.getName()).orElse(null);
        return user != null ? user.getId() : null;
    }

    @GetMapping("")
    public String index(Model model) {
        String userId = getUserId();
        if (userId == null) return "redirect:/login";

        ShoppingCartVM cartVM = new ShoppingCartVM();
        cartVM.setShoppingCartList(shoppingCartRepository.findByApplicationUserId(userId));
        cartVM.setOrderHeader(new OrderHeader());

        double total = 0;
        for (ShoppingCart cart : cartVM.getShoppingCartList()) {
            cart.setPrice(cart.getProduct().getPrice());
            total += (cart.getPrice() * cart.getCount());
        }
        cartVM.getOrderHeader().setOrderTotal(total);

        model.addAttribute("cartVM", cartVM);
        return "customer/cart/index";
    }

    @GetMapping("/summary")
    public String summary(Model model) {
        String userId = getUserId();
        ShoppingCartVM cartVM = new ShoppingCartVM();
        cartVM.setShoppingCartList(shoppingCartRepository.findByApplicationUserId(userId));
        cartVM.setOrderHeader(new OrderHeader());

        ApplicationUser user = applicationUserRepository.findById(userId).orElse(null);
        cartVM.getOrderHeader().setApplicationUser(user);
        cartVM.getOrderHeader().setName(user.getName());
        cartVM.getOrderHeader().setPhoneNumber(user.getPhoneNumber());
        cartVM.getOrderHeader().setStreetAddress(user.getStreetAddress());
        cartVM.getOrderHeader().setCity(user.getCity());
        cartVM.getOrderHeader().setPostalCode(user.getPostalCode());

        double total = 0;
        for (ShoppingCart cart : cartVM.getShoppingCartList()) {
            cart.setPrice(cart.getProduct().getPrice());
            total += (cart.getPrice() * cart.getCount());
        }
        cartVM.getOrderHeader().setOrderTotal(total);

        model.addAttribute("cartVM", cartVM);
        return "customer/cart/summary";
    }

    @PostMapping("/summary")
    public String summaryPOST(@Valid @ModelAttribute("cartVM") ShoppingCartVM cartVM, BindingResult result, HttpSession session) {
        String userId = getUserId();
        cartVM.setShoppingCartList(shoppingCartRepository.findByApplicationUserId(userId));
        
        double total = 0;
        for (ShoppingCart cart : cartVM.getShoppingCartList()) {
            cart.setPrice(cart.getProduct().getPrice());
            total += (cart.getPrice() * cart.getCount());
        }
        cartVM.getOrderHeader().setOrderTotal(total);
        cartVM.getOrderHeader().setOrderDate(LocalDateTime.now());
        cartVM.getOrderHeader().setApplicationUser(applicationUserRepository.findById(userId).orElse(null));

        // Promo code check
        if (cartVM.getOrderHeader().getPromoCode() != null && cartVM.getOrderHeader().getPromoCode().equalsIgnoreCase("DOCSACH")) {
            double discount = Math.round(total * 0.1);
            cartVM.getOrderHeader().setDiscountAmount(discount);
            cartVM.getOrderHeader().setOrderTotal(total - discount);
        }

        if (result.hasFieldErrors("orderHeader.cardNumber") || cartVM.getOrderHeader().getCardNumber() == null) {
            return "customer/cart/summary";
        }

        cartVM.getOrderHeader().setOrderStatus("Approved");
        cartVM.getOrderHeader().setPaymentStatus("Approved");
        cartVM.getOrderHeader().setPaymentDate(LocalDateTime.now());

        orderHeaderRepository.save(cartVM.getOrderHeader());

        for (ShoppingCart cart : cartVM.getShoppingCartList()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrderHeader(cartVM.getOrderHeader());
            detail.setProduct(cart.getProduct());
            detail.setCount(cart.getCount());
            detail.setPrice(cart.getPrice());
            orderDetailRepository.save(detail);

            // Update Stock
            Product product = cart.getProduct();
            product.setStockCount(product.getStockCount() - cart.getCount());
            productRepository.save(product);
        }

        shoppingCartRepository.deleteAll(cartVM.getShoppingCartList());
        session.setAttribute("SessionCart", 0);

        return "redirect:/customer/cart/confirmation/" + cartVM.getOrderHeader().getId();
    }

    @GetMapping("/confirmation/{id}")
    public String confirmation(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "customer/cart/confirmation";
    }

    @GetMapping("/plus/{cartId}")
    public String plus(@PathVariable("cartId") Integer cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            cart.setCount(cart.getCount() + 1);
            shoppingCartRepository.save(cart);
        }
        return "redirect:/customer/cart";
    }

    @GetMapping("/minus/{cartId}")
    public String minus(@PathVariable("cartId") Integer cartId, HttpSession session) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            if (cart.getCount() <= 1) {
                shoppingCartRepository.delete(cart);
                String userId = getUserId();
                session.setAttribute("SessionCart", shoppingCartRepository.countByApplicationUserId(userId).intValue());
            } else {
                cart.setCount(cart.getCount() - 1);
                shoppingCartRepository.save(cart);
            }
        }
        return "redirect:/customer/cart";
    }

    @GetMapping("/remove/{cartId}")
    public String remove(@PathVariable("cartId") Integer cartId, HttpSession session) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            shoppingCartRepository.delete(cart);
            String userId = getUserId();
            session.setAttribute("SessionCart", shoppingCartRepository.countByApplicationUserId(userId).intValue());
        }
        return "redirect:/customer/cart";
    }
}
