package com.mziuri.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mziuri.Product;
import com.mziuri.request.AddProductRequest;
import com.mziuri.request.PurchaseRequest;
import com.mziuri.response.AddProductResponse;
import com.mziuri.response.GetProductInfoResponse;
import com.mziuri.response.PurchaseResponse;
import com.mziuri.servers_and_storage.DatabaseManager;
import com.mziuri.servers_and_storage.StorageConfig;
import com.mziuri.servers_and_storage.StorageReader;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productName = request.getParameter("name");
        if (productName != null) {
            Product product = (Product) DatabaseManager.getInstance().getProducts(productName);
            if (product != null) {
                GetProductInfoResponse infoResponse = new GetProductInfoResponse();
                infoResponse.setName(product.getProd_name());
                infoResponse.setPrice(product.getProd_price());
                infoResponse.setAmount(product.getProd_amount());
                String jsonResponse = new ObjectMapper().writeValueAsString(infoResponse);
                response.setContentType("application/json");
                response.getWriter().write(jsonResponse);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing 'name' parameter");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PurchaseRequest purchaseRequest = objectMapper.readValue(request.getReader(), PurchaseRequest.class);
        String productName = purchaseRequest.getName();
        int requestedAmount = purchaseRequest.getAmount();
        if (productName != null && requestedAmount > 0) {
            Product product = DatabaseManager.getInstance().getProductByName(productName);
            if (product != null) {
                int availableAmount = product.getProd_amount();

                if (availableAmount >= requestedAmount) {
                    int remainingAmount = availableAmount - requestedAmount;
                    product.setProd_amount(remainingAmount);
                    DatabaseManager.getInstance().updateProduct(product);
                    PurchaseResponse purchaseResponse = new PurchaseResponse();
                    purchaseResponse.setName(productName);
                    purchaseResponse.setRemainingAmount(remainingAmount);
                    String jsonResponse = objectMapper.writeValueAsString(purchaseResponse);
                    response.setContentType("application/json");
                    response.getWriter().write(jsonResponse);
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Requested amount exceeds available quantity");
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request parameters");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        AddProductRequest addProductRequest = objectMapper.readValue(request.getReader(), AddProductRequest.class);

        String password = addProductRequest.getPassword();
        String productName = addProductRequest.getName();
        int amount = addProductRequest.getAmount();

        StorageConfig storageConfig = StorageReader.getInstance().readStorageConfig("src/main/resources/storage.json");
        if (storageConfig != null && password.equals(storageConfig.getPassword())) {
            Product product = DatabaseManager.getInstance().getProductByName(productName);

            if (product != null) {
                product.setProd_amount(product.getProd_amount() + amount);
                DatabaseManager.getInstance().updateProduct(product);

                AddProductResponse addProductResponse = new AddProductResponse();
                addProductResponse.setName(productName);
                addProductResponse.setRemainingAmount(product.getProd_amount());
                String jsonResponse = objectMapper.writeValueAsString(addProductResponse);
                response.setContentType("application/json");

                response.getWriter().write(jsonResponse);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found");
            }
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid password");
        }
    }
}
