package com.mziuri.response;

public class AddProductResponse {
    private String password;
    private String name;
    private Integer amount;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setRemainingAmount(int prodAmount) {
        this.amount = prodAmount;

    }
}
