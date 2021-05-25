package com.example.vgxchange.model;

import java.io.Serializable;

public class Payment implements Serializable {
    public String id;
    public double amount;
    public String creationDate;
    public int status;
    public User user;
    public BuyingProp proposal;

    public Payment(String id, double amount, String creationDate, int status, com.example.vgxchange.model.User user, BuyingProp proposal) {
        this.id = id;
        this.amount = amount;
        this.creationDate = creationDate;
        this.status = status;
        this.user = user;
        this.proposal = proposal;
    }

    public Payment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public com.example.vgxchange.model.User getUser() {
        return user;
    }

    public void setUser(com.example.vgxchange.model.User user) {
        this.user = user;
    }

    public BuyingProp getProposal() {
        return proposal;
    }

    public void setProposal(BuyingProp proposal) {
        this.proposal = proposal;
    }
}
