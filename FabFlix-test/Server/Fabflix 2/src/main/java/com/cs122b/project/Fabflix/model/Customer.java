package com.cs122b.project.Fabflix.model;

public class Customer {
    String id;

    String firstName;

    String lastName;

    CreditCard creditCard;

    String address;

    String email;

    String password;

    public Customer() {
    }

    public Customer(String id, String firstName, String lastName, CreditCard creditCard, String address, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCard = creditCard;
        this.address = address;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
