package com.mziuri.servers_and_storage;

import com.mziuri.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StorageConfig {
    @JsonProperty("products")
    private Product[] products;
    @JsonProperty("password")
    private String password;

    public Product[] getProducts() {
        return products;
    }

    public String getPassword() {
        return password;
    }
}
