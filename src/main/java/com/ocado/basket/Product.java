package com.ocado.basket;

import java.util.List;

/*
 * This class represents a product that can be delivered by one or more delivery methods.
 */

public class Product {
    private final String productName;
    private final List<String> deliveryMethods;

    public Product(String productName, List<String> deliveryMethods) {
        this.productName = productName;
        this.deliveryMethods = deliveryMethods;
    }

    public String getProductName() {
        return productName;
    }

    public List<String> getDeliveryMethods() {
        return deliveryMethods;
    }
}
