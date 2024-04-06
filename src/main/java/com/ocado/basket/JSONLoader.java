package com.ocado.basket;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/*
 * This class loads a delivery methods of given product from a JSON file.
 */

public class JSONLoader {
    public static Product loadDeliveryMethods(String absolutePathToConfigFile, String item) {
        JSONParser parser = new JSONParser();
        List<String> deliveryMethods = new ArrayList<>();
        try {
            Object obj = parser.parse(new FileReader(absolutePathToConfigFile));
            JSONObject jsonObject = (JSONObject) obj;
            Object delivery = jsonObject.get(item);
            if (delivery != null) {
                JSONArray deliveryMethodsArray = (JSONArray) delivery;
                for (Object method : deliveryMethodsArray) {
                    deliveryMethods.add((String) method);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Product(item, deliveryMethods);
    }
}
