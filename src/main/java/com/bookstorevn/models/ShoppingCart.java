package com.bookstorevn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "ShoppingCarts")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApplicationUserId", nullable = false)
    private ApplicationUser applicationUser;

    @Min(1)
    @Max(100)
    private Integer count;

    @Transient
    private Double price;

    public ShoppingCart() {
        this.count = 1;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public ApplicationUser getApplicationUser() { return applicationUser; }
    public void setApplicationUser(ApplicationUser applicationUser) { this.applicationUser = applicationUser; }

    public Integer getCount() { return count; }
    public void setCount(Integer count) { this.count = count; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
