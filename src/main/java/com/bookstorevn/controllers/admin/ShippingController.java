package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.OrderHeader;
import com.bookstorevn.repositories.OrderHeaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/shipping")
public class ShippingController {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @GetMapping("")
    public String index(Model model) {
        List<String> statuses = Arrays.asList("Approved", "InProcess", "Shipped");
        List<OrderHeader> orderList = orderHeaderRepository.findByOrderStatusIn(statuses);
        model.addAttribute("orderList", orderList);
        return "admin/shipping/index";
    }

    @PostMapping("/updateCarrier")
    public String updateCarrier(@RequestParam("orderId") Integer orderId, 
                                @RequestParam("carrier") String carrier, 
                                @RequestParam("trackingNumber") String trackingNumber, 
                                RedirectAttributes redirectAttributes) {
        OrderHeader orderHeader = orderHeaderRepository.findById(orderId).orElse(null);
        if (orderHeader == null) return "error/404";

        orderHeader.setCarrier(carrier);
        orderHeader.setTrackingNumber(trackingNumber);
        orderHeaderRepository.save(orderHeader);

        redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin vận chuyển thành công");
        return "redirect:/admin/shipping";
    }
}
