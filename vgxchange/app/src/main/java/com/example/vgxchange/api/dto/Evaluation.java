package com.example.vgxchange.api.dto;

public class Evaluation {
    public String RatingUser ;
    public String SellingProposalId ;
    public int rating;
    public String comment;

    public Evaluation(String idRatingUser, String idRatingProduct, int rating, String comment) {
        this.RatingUser = idRatingUser;
        this.SellingProposalId = idRatingProduct;
        this.rating = rating;
        this.comment = comment;
    }

    public String getIdRatingUser() {
        return RatingUser;
    }

    public String getIdRatingProduct() {
        return SellingProposalId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public void setIdRatingUser(String idRatingUser) {
        this.RatingUser = idRatingUser;
    }

    public void setIdRatingProduct(String idRatingProduct) {
        this.SellingProposalId = idRatingProduct;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
