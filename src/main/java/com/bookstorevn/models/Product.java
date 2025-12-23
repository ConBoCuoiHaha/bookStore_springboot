package com.bookstorevn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Tiêu đề sách là bắt buộc")
    @Size(max = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Mã ISBN là bắt buộc")
    private String isbn;

    @NotBlank(message = "Tên tác giả là bắt buộc")
    private String author;

    @NotNull(message = "Giá niêm yết là bắt buộc")
    @Min(value = 1)
    @Max(value = 10000000)
    private Double listPrice;

    @NotNull(message = "Giá bán là bắt buộc")
    @Min(value = 1)
    @Max(value = 10000000)
    private Double price;

    private String imageUrl;

    @NotNull(message = "Số lượng tồn kho là bắt buộc")
    @Min(value = 0)
    @Max(value = 10000)
    private Integer stockCount;

    private String publisherName;

    @Min(1000)
    @Max(2100)
    private Integer publishYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubcategoryId", nullable = false)
    private Subcategory subcategory;

    private Double weight;

    private String dimensions;

    public Product() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Double getListPrice() { return listPrice; }
    public void setListPrice(Double listPrice) { this.listPrice = listPrice; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Integer getStockCount() { return stockCount; }
    public void setStockCount(Integer stockCount) { this.stockCount = stockCount; }

    public String getPublisherName() { return publisherName; }
    public void setPublisherName(String publisherName) { this.publisherName = publisherName; }

    public Integer getPublishYear() { return publishYear; }
    public void setPublishYear(Integer publishYear) { this.publishYear = publishYear; }

    public Subcategory getSubcategory() { return subcategory; }
    public void setSubcategory(Subcategory subcategory) { this.subcategory = subcategory; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
}
