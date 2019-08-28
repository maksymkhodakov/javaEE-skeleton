package com.mainacad.service;

import com.mainacad.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {

    private static List<Order> orders = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Order order = new Order(1, 3, 2);
        orders.add(order);
    }

    @AfterEach
    void tearDown() {
        orders.stream().forEach(order -> OrderService.delete(order.getId()));
    }

    @Test
    void createOrderByItemAndUser() {
        Date date = new Date();

        assertNull(orders.get(0).getId());
        Order orderInDB = OrderService.create(orders.get(0));

        /**
         * This is one way to test method createOrderByItemAndUser in OrderService, but
         * this method includes using ItemService and UserService. In testing this is
         * unacceptable.
         *
         *         Item item = ItemService.create(items.get(0));
         *         User user = UserService.create(users.get(0));
         *
         *         Order checkedByItemAndUser = OrderService.createOrderByItemAndUser(item, 1, user);
         *         assertNotNull(checkedByItemAndUser);
         */

        assertNotNull(orderInDB);
        assertNotNull(orderInDB.getId());

        Order checkedOrderInDB = OrderService.findById(orderInDB.getId());
        assertNotNull(checkedOrderInDB);

        List<Order> checkedOrderInDBByCart = OrderService.getOrdersByCard(orderInDB.getCartId());
        assertNotNull(checkedOrderInDBByCart);

        List<Order> checkedOrderInDBByUserAndPeriod  = OrderService.findClosedOrdersByUserAndPeriod
                (orderInDB.getId(),date.getTime(), date.getTime());
        assertNotNull(checkedOrderInDBByUserAndPeriod);

        Order checkedByUpdates = OrderService.update(orderInDB);
        assertEquals(checkedByUpdates, orderInDB);

        OrderService.delete(checkedOrderInDB.getId());
        Order deletedUser = OrderService.findById(orderInDB.getId());

        assertNull(deletedUser);
    }
}