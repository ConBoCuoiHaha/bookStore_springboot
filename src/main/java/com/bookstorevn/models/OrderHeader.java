package com.bookstorevn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "OrderHeaders")
public class OrderHeader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApplicationUserId", nullable = false)
    private ApplicationUser applicationUser;

    @NotNull
    private LocalDateTime orderDate;
    private LocalDateTime shippingDate;
    private Double orderTotal;

    private String orderStatus;
    private String paymentStatus;
    private String trackingNumber;
    private String carrier;

    private LocalDateTime paymentDate;
    private LocalDateTime paymentDueDate;

    @NotBlank(message = "Họ tên là bắt buộc")
    private String name;

    @NotBlank(message = "Số điện thoại là bắt buộc")
    private String phoneNumber;

    @NotBlank(message = "Địa chỉ là bắt buộc")
    private String streetAddress;

    @NotBlank(message = "Thành phố là bắt buộc")
    private String city;

    @NotBlank(message = "Mã bưu điện là bắt buộc")
    private String postalCode;

    private String promoCode;
    private Double discountAmount;

    // payment details (demo)
    @Transient // Not saving card info to DB for security, just for demo form
    private String cardNumber;
    
    @Transient
    private String cardHolderName;

    public OrderHeader() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public ApplicationUser getApplicationUser() { return applicationUser; }
    public void setApplicationUser(ApplicationUser applicationUser) { this.applicationUser = applicationUser; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public LocalDateTime getShippingDate() { return shippingDate; }
    public void setShippingDate(LocalDateTime shippingDate) { this.shippingDate = shippingDate; }

    public Double getOrderTotal() { return orderTotal; }
    public void setOrderTotal(Double orderTotal) { this.orderTotal = orderTotal; }

    public String getOrderStatus() { return orderStatus; }
    public void setOrderStatus(String orderStatus) { this.orderStatus = orderStatus; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getCarrier() { return carrier; }
    public void setCarrier(String carrier) { this.carrier = carrier; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public LocalDateTime getPaymentDueDate() { return paymentDueDate; }
    public void setPaymentDueDate(LocalDateTime paymentDueDate) { this.paymentDueDate = paymentDueDate; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getPromoCode() { return promoCode; }
    public void setPromoCode(String promoCode) { this.promoCode = promoCode; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }

    public String getCardHolderName() { return cardHolderName; }
    public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
}
