package com.senior.challenge.user.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProductsDTO {
    private Map<String, Integer> products = new LinkedHashMap<>();

    @JsonAnySetter
    void setProduct(String key, Integer value) {
        products.put(key, value);
    }

    public Map<String, Integer> getProducts(){
        return this.products;
    }
}
