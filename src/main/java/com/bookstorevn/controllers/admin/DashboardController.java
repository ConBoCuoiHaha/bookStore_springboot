package com.bookstorevn.controllers.admin;

import com.bookstorevn.models.Category;
import com.bookstorevn.models.OrderHeader;
import com.bookstorevn.models.Product;
import com.bookstorevn.models.ViewModels.DashboardVM;
import com.bookstorevn.repositories.CategoryRepository;
import com.bookstorevn.repositories.OrderHeaderRepository;
import com.bookstorevn.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/dashboard")
public class DashboardController {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("")
    public String index(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        LocalDateTime start = startDate != null ? startDate.atStartOfDay() : LocalDate.now().minusDays(30).atStartOfDay();
        LocalDateTime end = endDate != null ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now();

        List<OrderHeader> orders = orderHeaderRepository.findByOrderStatusNotAndOrderDateBetween("Cancelled", start, end);
        List<Product> products = productRepository.findAll();

        DashboardVM dashboardVM = new DashboardVM();
        dashboardVM.setTotalRevenue(orders.stream().mapToDouble(OrderHeader::getOrderTotal).sum());
        dashboardVM.setTotalOrders(orders.size());
        dashboardVM.setTotalProducts(products.size());
        dashboardVM.setOutOfStockCount((int) products.stream().filter(p -> p.getStockCount() <= 0).count());
        dashboardVM.setStartDate(start);
        dashboardVM.setEndDate(end);

        // Revenue chart data
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate());
        if (daysBetween <= 62) {
            // Daily
            for (LocalDate date = start.toLocalDate(); !date.isAfter(end.toLocalDate()); date = date.plusDays(1)) {
                final LocalDate d = date;
                double dailyRevenue = orders.stream()
                        .filter(o -> o.getOrderDate().toLocalDate().equals(d))
                        .mapToDouble(OrderHeader::getOrderTotal)
                        .sum();
                DashboardVM.RevenueDataPoint pt = new DashboardVM.RevenueDataPoint();
                pt.setLabel(date.format(DateTimeFormatter.ofPattern("dd/MM")));
                pt.setValue(dailyRevenue);
                dashboardVM.getRevenueByMonth().add(pt);
            }
        } else {
            // Monthly
            for (LocalDate date = start.toLocalDate().withDayOfMonth(1); !date.isAfter(end.toLocalDate()); date = date.plusMonths(1)) {
                final int month = date.getMonthValue();
                final int year = date.getYear();
                double monthlyRevenue = orders.stream()
                        .filter(o -> o.getOrderDate().getMonthValue() == month && o.getOrderDate().getYear() == year)
                        .mapToDouble(OrderHeader::getOrderTotal)
                        .sum();
                DashboardVM.RevenueDataPoint pt = new DashboardVM.RevenueDataPoint();
                pt.setLabel(date.format(DateTimeFormatter.ofPattern("MM/yyyy")));
                pt.setValue(monthlyRevenue);
                dashboardVM.getRevenueByMonth().add(pt);
            }
        }

        // Stock by Category
        List<Category> categories = categoryRepository.findAll();
        for (Category cat : categories) {
            int stock = products.stream()
                    .filter(p -> p.getSubcategory() != null && p.getSubcategory().getCategory() != null && p.getSubcategory().getCategory().getId().equals(cat.getId()))
                    .mapToInt(Product::getStockCount)
                    .sum();
            DashboardVM.InventoryDataPoint pt = new DashboardVM.InventoryDataPoint();
            pt.setLabel(cat.getName());
            pt.setValue(stock);
            dashboardVM.getStockByCategory().add(pt);
        }

        model.addAttribute("dashboardVM", dashboardVM);
        model.addAttribute("revenueLabels", dashboardVM.getRevenueByMonth().stream().map(DashboardVM.RevenueDataPoint::getLabel).collect(Collectors.toList()));
        model.addAttribute("revenueValues", dashboardVM.getRevenueByMonth().stream().map(DashboardVM.RevenueDataPoint::getValue).collect(Collectors.toList()));
        model.addAttribute("stockLabels", dashboardVM.getStockByCategory().stream().map(DashboardVM.InventoryDataPoint::getLabel).collect(Collectors.toList()));
        model.addAttribute("stockValues", dashboardVM.getStockByCategory().stream().map(DashboardVM.InventoryDataPoint::getValue).collect(Collectors.toList()));
        
        return "admin/dashboard/index";
    }

    @GetMapping("/inventory-details")
    @ResponseBody
    public Map<String, Object> getInventoryDetails() {
        List<Product> products = productRepository.findAll();
        System.out.println("Dashboard Inventory: Found " + products.size() + " products");
        
        List<Map<String, Object>> data = products.stream()
                .map(p -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("title", p.getTitle() != null ? p.getTitle() : "N/A");
                    map.put("category", p.getSubcategory() != null && p.getSubcategory().getCategory() != null ? p.getSubcategory().getCategory().getName() : "N/A");
                    map.put("subcategory", p.getSubcategory() != null ? p.getSubcategory().getName() : "N/A");
                    map.put("stock", p.getStockCount() != null ? p.getStockCount() : 0);
                    map.put("price", p.getPrice() != null ? p.getPrice() : 0.0);
                    return map;
                })
                .sorted((a, b) -> {
                    Integer s1 = (Integer) a.get("stock");
                    Integer s2 = (Integer) b.get("stock");
                    return s1.compareTo(s2);
                })
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        return result;
    }
}
