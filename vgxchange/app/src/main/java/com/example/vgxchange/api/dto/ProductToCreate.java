package com.example.vgxchange.api.dto;

public class ProductToCreate {
    public double Price;
    public String PhotoLink;

    public String AnnouncerId;
    public String GameId;


    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public String getPhotoLink() {
        return PhotoLink;
    }

    public void setPhotoLink(String photoLink) {
        PhotoLink = photoLink;
    }

    public String getAnnouncerId() {
        return AnnouncerId;
    }

    public void setAnnouncerId(String announcerId) {
        AnnouncerId = announcerId;
    }

    public String getGameId() {
        return GameId;
    }

    public void setGameId(String gameId) {
        GameId = gameId;
    }

    public ProductToCreate(double price, String photoLink, String idUser, String idGame) {
        Price = price;
        PhotoLink = photoLink;
        this.AnnouncerId = idUser;
        this.GameId = idGame;
    }


}


