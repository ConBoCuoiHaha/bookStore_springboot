package com.bookstorevn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "AspNetUsers")
public class ApplicationUser {
    @Id
    private String id;

    @NotBlank(message = "Họ tên là bắt buộc")
    private String name;

    private String streetAddress;
    private String city;
    private String postalCode;
    private String email;
    private String phoneNumber;
    private String userName;
    
    @Column(name = "PasswordHash")
    private String password;

    private String role;
    
    // Identity fields
    @Column(name = "LockoutEnd")
    private java.time.LocalDateTime lockoutEnd;

    public ApplicationUser() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStreetAddress() { return streetAddress; }
    public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public java.time.LocalDateTime getLockoutEnd() { return lockoutEnd; }
    public void setLockoutEnd(java.time.LocalDateTime lockoutEnd) { this.lockoutEnd = lockoutEnd; }

    public boolean isLocked() { 
        return lockoutEnd != null && lockoutEnd.isAfter(java.time.LocalDateTime.now()); 
    }
}
