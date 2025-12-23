package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.OrderDetail;
import com.bookstorevn.models.OrderHeader;
import com.bookstorevn.repositories.OrderDetailRepository;
import com.bookstorevn.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("")
    public String index(Model model) {
        List<OrderHeader> orderHeaders = orderHeaderRepository.findAll();
        model.addAttribute("orderHeaders", orderHeaders);
        return "admin/order/index";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Integer id, Model model) {
        return getOrderDetails(id, model);
    }

    @GetMapping("/details")
    public String detailsByParam(@RequestParam("id") Integer id, Model model) {
        return getOrderDetails(id, model);
    }

    private String getOrderDetails(Integer id, Model model) {
        OrderHeader orderHeader = orderHeaderRepository.findById(id).orElse(null);
        if (orderHeader == null) return "error/404";
        
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderHeaderId(id);
        model.addAttribute("orderHeader", orderHeader);
        model.addAttribute("orderDetails", orderDetails);
        return "admin/order/details";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam("id") Integer id, @RequestParam("status") String status, RedirectAttributes redirectAttributes) {
        OrderHeader orderHeader = orderHeaderRepository.findById(id).orElse(null);
        if (orderHeader != null) {
            orderHeader.setOrderStatus(status);
            if ("Shipped".equalsIgnoreCase(status)) {
                orderHeader.setShippingDate(LocalDateTime.now());
            }
            orderHeaderRepository.save(orderHeader);
            redirectAttributes.addFlashAttribute("success", "Trạng thái đơn hàng đã được cập nhật: " + status);
        }
        return "redirect:/admin/order/details/" + id;
    }
}
