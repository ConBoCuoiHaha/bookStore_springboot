package com.bookstorevn.models.ViewModels;

import com.bookstorevn.models.ProductReview;
import com.bookstorevn.models.ShoppingCart;
import java.util.List;

public class ProductDetailsVM {
    private ShoppingCart shoppingCart;
    private List<ProductReview> reviews;
    private double averageRating;
    private boolean hasOrdered;

    public ProductDetailsVM() {}

    public ShoppingCart getShoppingCart() { return shoppingCart; }
    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    public List<ProductReview> getReviews() { return reviews; }
    public void setReviews(List<ProductReview> reviews) { this.reviews = reviews; }

    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }

    public boolean isHasOrdered() { return hasOrdered; }
    public void setHasOrdered(boolean hasOrdered) { this.hasOrdered = hasOrdered; }
}
