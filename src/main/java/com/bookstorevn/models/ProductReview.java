package com.bookstorevn.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ProductReviews")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProductId", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApplicationUserId", nullable = false)
    private ApplicationUser applicationUser;

    private Integer rating;
    @Column(columnDefinition = "TEXT")
    private String comment;
    private LocalDateTime reviewDate;

    public ProductReview() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public ApplicationUser getApplicationUser() { return applicationUser; }
    public void setApplicationUser(ApplicationUser applicationUser) { this.applicationUser = applicationUser; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }
}
