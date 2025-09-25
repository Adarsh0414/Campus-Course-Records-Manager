package edu.ccrm.domain;

import java.time.LocalDateTime;

public abstract class Person {
    private Name fullName;
    private String email;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Person(Name fullName, String email) {
        this.fullName = fullName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Abstract method to be implemented by subclasses
    public abstract String getDetails();

    // Getters and Setters
    public Name getFullName() {
        return fullName;
    }

    public void setFullName(Name fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return getDetails(); // Use the abstract method for polymorphic string representation
    }
}
