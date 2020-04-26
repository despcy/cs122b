package com.cs122b.project.Fabflix.model;

import java.util.ArrayList;

public class CartItem {
    private String movieId;
    private String title;
    private  int price;
    private int quantity;
    private ArrayList<String> sid;

    public CartItem(String movieId, String title, int price, int quantity) {
        this.movieId = movieId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public CartItem(String movieId, String title, int price, int quantity, ArrayList<String> sid) {
        this.movieId = movieId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.sid = sid;
    }

    public ArrayList<String> getSid() {
        return sid;
    }

    public void setSid(ArrayList<String> sid) {
        this.sid = sid;
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
