package com.vachana.qa.models;

public record Customer(
        String name,
        String email,
        String password,
        String firstName,
        String lastName,
        String company,
        String address1,
        String address2,
        String country,
        String state,
        String city,
        String zipcode,
        String mobileNumber
) {
    public String fullName() {
        return firstName + " " + lastName;
    }
}
