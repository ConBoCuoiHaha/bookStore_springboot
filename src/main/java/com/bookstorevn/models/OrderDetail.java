package com.bookstorevn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "OrderHeaderId", nullable = false)
    private OrderHeader orderHeader;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;

    @NotNull
    private Integer count;
    
    @NotNull
    private Double price;

    public OrderDetail() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public OrderHeader getOrderHeader() { return orderHeader; }
    public void setOrderHeader(OrderHeader orderHeader) { this.orderHeader = orderHeader; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
