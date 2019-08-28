package com.mainacad.service;

import com.mainacad.dao.CartDAO;
import com.mainacad.dao.OrderDAO;
import com.mainacad.model.Cart;
import com.mainacad.model.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartService {

    public static Cart createCartForUser(Integer userId){
        Cart cart = new Cart();

        cart.setCreationTime(new Date().getTime());
        cart.setClosed(Boolean.FALSE);
        cart.setUserId(userId);

        Cart storedCart = CartDAO.create(cart);

        return storedCart;
    }

    public static Cart update(Cart cart){
        return CartDAO.update(cart);
    }

    public static Cart close(Integer cartId){
        return CartDAO.close(cartId);
    }

    public static List<Cart> findByUser(Integer userId){
        return CartDAO.findByUser(userId);
    }

    public static Cart findById(Integer userId){
        return CartDAO.findById(userId);
    }

    public static Cart findOpenCartByUser(Integer userId){
        return CartDAO.findOpenCartByUser(userId);
    }

    public static List<Order> getOrdersFromOpenCartByUser(Integer userId) {
        List<Order> orders = new ArrayList<>();

        Cart openCart = CartDAO.findOpenCartByUser(userId);
        if (openCart != null) {
            orders = OrderDAO.findByCart(openCart.getId());
        }

        return orders;
    }
}
