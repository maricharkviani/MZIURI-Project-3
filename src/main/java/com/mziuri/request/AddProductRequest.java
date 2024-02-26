package com.mziuri.request;

public class AddProductRequest {
    private String name;
    private Integer remainingAmount;
    private String password;
    private int amount;

    public String getName() {
        return name;
    }


    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
