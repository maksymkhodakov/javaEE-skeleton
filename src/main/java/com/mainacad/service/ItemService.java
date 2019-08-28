package com.mainacad.service;

import com.mainacad.dao.ItemDAO;
import com.mainacad.model.Item;

import java.util.List;

public class ItemService {

    public static Item create(Item item){
        return ItemDAO.create(item);
    }

    public static Item update (Item item){
        return ItemDAO.update(item);
    }

    public static Item findById(Integer id){
        return ItemDAO.findById(id);
    }

    public static List<Item> findAll(){
        return ItemDAO.findAll();
    }

    public static Item findByItemCode(String itemCode){
        return ItemDAO.findByItemCode(itemCode);
    }

    public static List<Item> getSumOfAllOrdersByUserIdAndPeriod(Integer userId, Long from, Long to){
        return ItemDAO.getSumOfAllOrdersByUserIdAndPeriod(userId, from, to);
    }

    public static void delete(Integer id){
        ItemDAO.delete(id);
    }
}
