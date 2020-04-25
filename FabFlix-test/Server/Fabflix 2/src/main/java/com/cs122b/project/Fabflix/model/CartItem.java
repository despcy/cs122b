package com.cs122b.project.Fabflix.model;

public class CartItem {
    private String movieId;
    private String title;
    private  int price;
    private int quantity;

    public CartItem(String movieId, String title, int price, int quantity) {
        this.movieId = movieId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }
}
