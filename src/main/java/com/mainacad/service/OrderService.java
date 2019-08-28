package com.mainacad.service;

import com.mainacad.dao.OrderDAO;
import com.mainacad.model.Cart;
import com.mainacad.model.Item;
import com.mainacad.model.Order;
import com.mainacad.model.User;

import java.util.List;

public class OrderService {

    public static Order createOrderByItemAndUser(Integer itemId, Integer amount, User user){
        Order order = new Order();

        order.setItemId(itemId);
        order.setAmount(amount);

        // get or create open cart
        Cart cart = CartService.findOpenCartByUser(user.getId());
        if (cart == null) {
            cart = CartService.createCartForUser(user.getId());
        }
        order.setCartId(cart.getId());
        return OrderDAO.create(order);
    }

    public static Order create(Order order){
        return OrderDAO.create(order);
    }

    public static List<Order> getOrdersByCard(Integer cartId){
        return OrderDAO.findByCart(cartId);
    }

    public static Order findById(Integer id){
        return OrderDAO.findById(id);
    }

    public static List<Order> findClosedOrdersByUserAndPeriod(Integer userId, Long from, Long to){
        return OrderDAO.findClosedOrdersByUserAndPeriod(userId, from, to);
    }

    public static Order update(Order order){
        return OrderDAO.update(order);
    }

    public static void delete (Integer id){
        OrderDAO.delete(id);
    }

    public static Order createOrderByItemAndUser(Item item, Integer amount, User user){

        Order order = new Order();
        order.setItemId(item.getId());
        order.setAmount(amount);

        Cart cart = CartService.findOpenCartByUser(user.getId());

        if (cart == null) {
            cart = CartService.createCartForUser(user.getId());
        }
        order.setCartId(cart.getId());

        return OrderDAO.create(order);
    }

    public static Order addItemToOrder(Item item, User user){

        Order existingOrder = CartService.getOrdersFromOpenCartByUser(user.getId()).stream().
                filter(order -> order.getItemId().equals(item.getId())).findAny().orElse(null);

        if (existingOrder == null) {
            existingOrder = createOrderByItemAndUser(item, 1, user);

        } else {
            existingOrder.setAmount(existingOrder.getAmount() + 1);
            OrderDAO.update(existingOrder);
        }

        return existingOrder;
    }
}