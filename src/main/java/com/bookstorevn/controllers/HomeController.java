package com.bookstorevn.controllers;

import com.bookstorevn.models.*;
import com.bookstorevn.models.ViewModels.ProductDetailsVM;
import com.bookstorevn.repositories.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ProductReviewRepository productReviewRepository;

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    private String getUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) return null;
        ApplicationUser user = applicationUserRepository.findByEmail(auth.getName()).orElse(null);
        return user != null ? user.getId() : null;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "search", required = false) String search, Model model) {
        List<Product> productList = productRepository.findAll();
        if (search != null && !search.isEmpty()) {
            productList = productList.stream()
                    .filter(u -> u.getTitle().toLowerCase().contains(search.toLowerCase()) 
                            || u.getAuthor().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }
        model.addAttribute("productList", productList);
        return "customer/home/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return "error/404";

        List<ProductReview> reviews = productReviewRepository.findByProductId(id);
        
        ProductDetailsVM detailsVM = new ProductDetailsVM();
        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setCount(1);
        detailsVM.setShoppingCart(cart);
        detailsVM.setReviews(reviews);
        detailsVM.setAverageRating(reviews.isEmpty() ? 0 : reviews.stream().mapToInt(ProductReview::getRating).average().orElse(0));
        detailsVM.setHasOrdered(false);

        String userId = getUserId();
        if (userId != null) {
            List<OrderHeader> orders = orderHeaderRepository.findByApplicationUserId(userId);
            for (OrderHeader header : orders) {
                if (!"Cancelled".equalsIgnoreCase(header.getOrderStatus())) {
                    List<OrderDetail> details = orderDetailRepository.findByOrderHeaderId(header.getId());
                    if (details.stream().anyMatch(d -> d.getProduct().getId().equals(id))) {
                        detailsVM.setHasOrdered(true);
                        break;
                    }
                }
            }
        }

        model.addAttribute("detailsVM", detailsVM);
        return "customer/home/details";
    }

    @PostMapping("/details")
    public String details(@ModelAttribute("detailsVM") ProductDetailsVM detailsVM, HttpSession session, RedirectAttributes redirectAttributes) {
        String userId = getUserId();
        if (userId == null) return "redirect:/login";

        Product product = productRepository.findById(detailsVM.getShoppingCart().getProduct().getId()).orElse(null);
        ShoppingCart cartFromDb = shoppingCartRepository.findByApplicationUserIdAndProductId(userId, product.getId()).orElse(null);

        if (cartFromDb != null) {
            cartFromDb.setCount(cartFromDb.getCount() + detailsVM.getShoppingCart().getCount());
            shoppingCartRepository.save(cartFromDb);
            redirectAttributes.addFlashAttribute("success", "Đã cập nhật số lượng trong giỏ hàng");
        } else {
            ShoppingCart cart = detailsVM.getShoppingCart();
            cart.setApplicationUser(applicationUserRepository.findById(userId).orElse(null));
            cart.setProduct(product);
            shoppingCartRepository.save(cart);
            redirectAttributes.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng");
        }

        session.setAttribute("SessionCart", shoppingCartRepository.countByApplicationUserId(userId).intValue());
        return "redirect:/";
    }

    @PostMapping("/postReview")
    public String postReview(@RequestParam("productId") Integer productId, 
                             @RequestParam("rating") Integer rating, 
                             @RequestParam("comment") String comment, 
                             RedirectAttributes redirectAttributes) {
        String userId = getUserId();
        if (userId == null) return "redirect:/login";

        // Simple validation: check if ordered (can be enhanced)
        ProductReview review = new ProductReview();
        review.setProduct(productRepository.findById(productId).orElse(null));
        review.setApplicationUser(applicationUserRepository.findById(userId).orElse(null));
        review.setRating(rating);
        review.setComment(comment);
        review.setReviewDate(LocalDateTime.now());

        productReviewRepository.save(review);
        redirectAttributes.addFlashAttribute("success", "Cảm ơn bạn đã để lại đánh giá!");
        return "redirect:/details/" + productId;
    }
}
