package com.vachana.qa.api;

import com.vachana.qa.models.Customer;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ApiPayloadFactory {
    private ApiPayloadFactory() {
    }

    public static Map<String, Object> createAccountPayload(Customer customer) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("name", customer.name());
        payload.put("email", customer.email());
        payload.put("password", customer.password());
        payload.put("title", "Mr");
        payload.put("birth_date", "1");
        payload.put("birth_month", "January");
        payload.put("birth_year", "1990");
        payload.put("firstname", customer.firstName());
        payload.put("lastname", customer.lastName());
        payload.put("company", customer.company());
        payload.put("address1", customer.address1());
        payload.put("address2", customer.address2());
        payload.put("country", customer.country());
        payload.put("zipcode", customer.zipcode());
        payload.put("state", customer.state());
        payload.put("city", customer.city());
        payload.put("mobile_number", customer.mobileNumber());
        return payload;
    }

    public static Map<String, Object> loginPayload(Customer customer) {
        return Map.of("email", customer.email(), "password", customer.password());
    }

    public static Map<String, Object> deletePayload(Customer customer) {
        return loginPayload(customer);
    }
}
