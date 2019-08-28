package com.mainacad.service;

import com.mainacad.model.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CartServiceTest {

    private static List<Cart> carts = new ArrayList<>();
    private static Date date = new Date();

    @BeforeEach
    void setUp() {
        Cart cart = new Cart(date.getTime(), false, 2);
        carts.add(cart);
    }

    @Test
    void createCartForUser() {

        assertNull(carts.get(0).getId());
        Cart cartInDB = CartService.createCartForUser(2);

        assertNotNull(cartInDB);
        assertNotNull(cartInDB.getId());

        Cart checkedById = CartService.findById(cartInDB.getId());
        assertNotNull(checkedById);

        List<Cart> checkedByUser = CartService.findByUser(cartInDB.getUserId());
        assertNotNull(checkedByUser);

        Cart checkedOpenCardByUser = CartService.findOpenCartByUser(cartInDB.getUserId());
        assertNotNull(checkedOpenCardByUser);

        Cart checkedByUpdate = CartService.update(cartInDB);
        assertEquals(checkedByUpdate, cartInDB);
    }
}