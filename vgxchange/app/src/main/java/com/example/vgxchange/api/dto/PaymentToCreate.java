package com.example.vgxchange.api.dto;

public class PaymentToCreate {
    public String ProposalId;
    public String UserId;
    public double Amount;

    public PaymentToCreate(String proposalId, String userId, double amount) {
        ProposalId = proposalId;
        UserId = userId;
        Amount = amount;
    }
}
