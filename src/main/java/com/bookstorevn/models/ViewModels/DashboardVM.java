package com.bookstorevn.models.ViewModels;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardVM {
    private double totalRevenue;
    private int totalOrders;
    private int totalProducts;
    private int outOfStockCount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private List<RevenueDataPoint> revenueByMonth = new ArrayList<>();
    private List<InventoryDataPoint> stockByCategory = new ArrayList<>();

    public DashboardVM() {}

    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }

    public int getTotalOrders() { return totalOrders; }
    public void setTotalOrders(int totalOrders) { this.totalOrders = totalOrders; }

    public int getTotalProducts() { return totalProducts; }
    public void setTotalProducts(int totalProducts) { this.totalProducts = totalProducts; }

    public int getOutOfStockCount() { return outOfStockCount; }
    public void setOutOfStockCount(int outOfStockCount) { this.outOfStockCount = outOfStockCount; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public List<RevenueDataPoint> getRevenueByMonth() { return revenueByMonth; }
    public void setRevenueByMonth(List<RevenueDataPoint> revenueByMonth) { this.revenueByMonth = revenueByMonth; }

    public List<InventoryDataPoint> getStockByCategory() { return stockByCategory; }
    public void setStockByCategory(List<InventoryDataPoint> stockByCategory) { this.stockByCategory = stockByCategory; }

    public static class RevenueDataPoint {
        private String label;
        private double value;

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public double getValue() { return value; }
        public void setValue(double value) { this.value = value; }
    }

    public static class InventoryDataPoint {
        private String label;
        private int value;

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public int getValue() { return value; }
        public void setValue(int value) { this.value = value; }
    }
}
