package com.cs122b.project.Fabflix.Response;

import com.cs122b.project.Fabflix.model.CartItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CartResponse {
    private Integer message;


    private ArrayList<CartItem> cartList;

    public CartResponse(Integer message, ArrayList<CartItem> cartList) {
        this.message = message;
        this.cartList = cartList;
    }

    public Integer getMessage() {
        return message;
    }

    public void setMessage(Integer message) {
        this.message = message;
    }

    @JsonProperty("data")
    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public void setCartList(ArrayList<CartItem> cartList) {
        this.cartList = cartList;
    }
}
