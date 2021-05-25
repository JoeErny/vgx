package com.example.vgxchange.api.dto;

import java.util.Date;

public class RentalPropositionToCreate {
    public String ProductAnnounceId;
    public String ProposingUserId;
    public Double proposedAmount;
    public Date RentalStart;
    public Date RentalEnd;

    public RentalPropositionToCreate(String productAnnounceId, String proposingUserId, Double proposedAmount, Date rentalStart, Date rentalEnd) {
        ProductAnnounceId = productAnnounceId;
        ProposingUserId = proposingUserId;
        this.proposedAmount = proposedAmount;
        RentalStart = rentalStart;
        RentalEnd = rentalEnd;
    }
}
