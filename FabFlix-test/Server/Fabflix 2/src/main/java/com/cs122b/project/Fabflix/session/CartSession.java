package com.cs122b.project.Fabflix.session;

import com.cs122b.project.Fabflix.model.CartItem;
import com.cs122b.project.Fabflix.model.Movie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class CartSession {
    private HashMap<String, CartItem> cartItems;//<movie-id, cart>

    public CartSession()
    {
        cartItems = new HashMap<String, CartItem>();
    }

    public void addListToCart(ArrayList<CartItem> cartList) {
        for(CartItem item:cartList) {
            //add to session
            cartItems.put(item.getMovieId(), item);
        }
    }

    public CartItem addItemToCart(String movieId, String title, int quantity)
    {
        Random rand = new Random();
        if (cartItems.containsKey(movieId))
        {
            CartItem existingItem = cartItems.get(movieId);
            existingItem.addQuantity(quantity);
            return existingItem;
        }
        else
        {
            CartItem cartItem = new CartItem(movieId, title, rand.nextInt(1000) ,quantity);
            cartItems.put(movieId, cartItem);
            return cartItem;
        }
    }

    public void updateQuantityOfItemInCart(CartItem item, int quantity)
    {
        if (cartItems.containsKey(item.getMovieId()))
        {
            CartItem existingItem = cartItems.get(item.getMovieId());
            existingItem.setQuantity(quantity);
        }
    }

    public void removeItemFromCart(CartItem item)
    {
        if (cartItems.containsKey(item.getMovieId()))
        {
            cartItems.remove(item.getMovieId());
        }
    }


    public void removeAllItemsFromCart()
    {
        cartItems.clear();
    }

    public ArrayList<CartItem> getCartItems()
    {
        return new ArrayList<CartItem>(cartItems.values());
    }
}
