package com.example.vgxchange.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Entity(tableName = "buying_proposition")
public class BuyingProp implements Serializable {

    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "id_buying_proposition")
    public String id;
    public String dateSubmission;
    public double proposedAmount;
    public int propositionState;
    public int propositionType;
    public String rentalStart;
    public String rentalEnd;

    @Embedded(prefix = "buying_proposition_product_")
    public ProductAnnounce productAnnounce;
    @Embedded(prefix = "buying_proposition_user_")
    public User proposingUser;

    public BuyingProp() {
    }

    public BuyingProp(@NotNull String id, String dateSubmission, double proposedAmount, int propositionState, ProductAnnounce productAnnounce, User proposingUser) {
        this.id = id;
        this.dateSubmission = dateSubmission;
        this.proposedAmount = proposedAmount;
        this.propositionState = propositionState;
        this.productAnnounce = productAnnounce;
        this.proposingUser = proposingUser;
    }

    public BuyingProp(@NotNull String id, String dateSubmission, double proposedAmount, int propositionState, int propositionType, ProductAnnounce productAnnounce, User proposingUser) {
        this.id = id;
        this.dateSubmission = dateSubmission;
        this.proposedAmount = proposedAmount;
        this.propositionState = propositionState;
        this.propositionType = propositionType;
        this.productAnnounce = productAnnounce;
        this.proposingUser = proposingUser;
    }

    public BuyingProp(@NotNull String id, String dateSubmission, double proposedAmount, int propositionState, int propositionType, String rentalStart, String rentalEnd, ProductAnnounce productAnnounce, User proposingUser) {
        this.id = id;
        this.dateSubmission = dateSubmission;
        this.proposedAmount = proposedAmount;
        this.propositionState = propositionState;
        this.propositionType = propositionType;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.productAnnounce = productAnnounce;
        this.proposingUser = proposingUser;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getDateSubmission() {
        return dateSubmission;
    }

    public void setDateSubmission(String dateSubmission) {
        this.dateSubmission = dateSubmission;
    }

    public double getProposedAmount() {
        return proposedAmount;
    }

    public void setProposedAmount(double proposedAmount) {
        this.proposedAmount = proposedAmount;
    }

    public int getPropositionState() {
        return propositionState;
    }

    public void setPropositionState(int propositionState) {
        this.propositionState = propositionState;
    }

    public ProductAnnounce getProductAnnounce() {
        return productAnnounce;
    }

    public void setProductAnnounce(ProductAnnounce productAnnounce) {
        this.productAnnounce = productAnnounce;
    }

    public User getProposingUser() {
        return proposingUser;
    }

    public void setProposingUser(User proposingUser) {
        this.proposingUser = proposingUser;
    }

    public int getPropositionType() {
        return propositionType;
    }

    public void setPropositionType(int propositionType) {
        this.propositionType = propositionType;
    }

    public String getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(String rentalStart) {
        this.rentalStart = rentalStart;
    }

    public String getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(String rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

}
