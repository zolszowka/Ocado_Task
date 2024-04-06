package com.ocado.basket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BasketSplitter {
    public final String absolutePathToConfigFile;

    public BasketSplitter(String absolutePathToConfigFile) {
        this.absolutePathToConfigFile = absolutePathToConfigFile;
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, Integer> deliveryMethods = new HashMap<>();
        Map<String, List<String>> result = new HashMap<>();
        List<Product> productsToSplit = new ArrayList<>();

        // Load the shipping methods from the configuration file for each product and count the number of occurrences of each shipping method.
        for (String item : items) {
            Product product = JSONLoader.loadDeliveryMethods(absolutePathToConfigFile, item);
            productsToSplit.add(product);
            for (String method : product.getDeliveryMethods()) {
                if (deliveryMethods.containsKey(method)) {
                    deliveryMethods.put(method, deliveryMethods.get(method) + 1);
                } else {
                    deliveryMethods.put(method, 1);
                }
            }
        }

        // Split the products into delivery methods.
        while (!productsToSplit.isEmpty()) {
            String bestDeliveryMethod = getBestDeliveryMethod(deliveryMethods);
            List<String> products = new ArrayList<>();
            List<Product> productsToSplitCopy = new ArrayList<>(productsToSplit);
            for (Product product : productsToSplitCopy) {
                List<String> deliveryMethodsForItem = product.getDeliveryMethods();
                if (deliveryMethodsForItem.contains(bestDeliveryMethod)) {
                    products.add(product.getProductName());
                    productsToSplit.remove(product);
                    for (String method : deliveryMethodsForItem) {
                        deliveryMethods.put(method, deliveryMethods.get(method) - 1);
                    }
                }
                result.put(bestDeliveryMethod, products);
            }
            deliveryMethods.remove(bestDeliveryMethod);
        }

        return result;
    }


    // Returns the delivery method that occurs the most frequently.
    public String getBestDeliveryMethod(Map<String, Integer> deliveryMethods) {
        String bestDeliveryMethod = null;
        int maximumOccurences = Integer.MIN_VALUE;
        for (Map.Entry<String, Integer> entry : deliveryMethods.entrySet()) {
            String deliveryMethod = entry.getKey();
            int numberOfOccurrences = entry.getValue();
            if (numberOfOccurrences > maximumOccurences) {
                maximumOccurences = numberOfOccurrences;
                bestDeliveryMethod = deliveryMethod;
            }
        }
        return bestDeliveryMethod;
    }
}
