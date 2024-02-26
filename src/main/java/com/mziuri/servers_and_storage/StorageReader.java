package com.mziuri.servers_and_storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mziuri.Product;
import java.io.File;
import java.io.IOException;
public class StorageReader {
    private static StorageReader instance;

    private StorageReader() {
    }

    public static synchronized StorageReader getInstance() {
        if (instance == null) {
            instance = new StorageReader();
        }
        return instance;
    }

    public void readAndAddProductsToDatabase(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            StorageConfig storageConfig = objectMapper.readValue(file, StorageConfig.class);
            for (Product product : storageConfig.getProducts()) {
                DatabaseManager.getInstance().addProduct(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StorageConfig readStorageConfig(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            return objectMapper.readValue(file, StorageConfig.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
