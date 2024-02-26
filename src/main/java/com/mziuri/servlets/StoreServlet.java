package com.mziuri.servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mziuri.response.GetProductsResponse;
import com.mziuri.servers_and_storage.DatabaseManager;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/StoreServlet")
public class StoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetProductsResponse productsResponse = new GetProductsResponse();
        productsResponse.setProductNames(DatabaseManager.getInstance().getProductNames());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(productsResponse);
        response.setContentType("application/json");
        response.getWriter().write(jsonResponse);
    }
}