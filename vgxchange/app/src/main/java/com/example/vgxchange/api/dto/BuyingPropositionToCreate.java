package com.example.vgxchange.api.dto;

public class BuyingPropositionToCreate {

    public String ProductAnnounceId;
    public String ProposingUserId;
    public Double proposedAmount;

    public BuyingPropositionToCreate(String productAnnounceId, String proposingUserId, Double proposedAmount) {
        ProductAnnounceId = productAnnounceId;
        ProposingUserId = proposingUserId;
        this.proposedAmount = proposedAmount;
    }
}
