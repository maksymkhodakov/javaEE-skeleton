package com.mainacad.service;

import com.mainacad.model.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest {

    private static List<Item> items = new ArrayList<>();

    @BeforeEach
    void setUp() {
        Item item = new Item("test_itemCode", "test_name",23);
        items.add(item);
    }

    @AfterEach
    void tearDown() {
        items.stream().forEach(item -> ItemService.delete(item.getId()));
    }

    @Test
    void create() {
        Date date = new Date();

        assertNull(items.get(0).getId());
        Item itemInDB = ItemService.create(items.get(0));

        assertNotNull(itemInDB);
        assertNotNull(itemInDB.getId());

        Item checkedByUpdate = ItemService.update(itemInDB);
        assertEquals(itemInDB, checkedByUpdate);

        Item checkedItemInDB = ItemService.findById(itemInDB.getId());
        assertNotNull(checkedItemInDB);

        Item checkedItemInDBByLogin = ItemService.findByItemCode(itemInDB.getItemCode());
        assertNotNull(checkedItemInDBByLogin);

        List<Item> checkedItemInDBByAll = ItemService.findAll();
        assertNotNull(checkedItemInDBByAll);

        List<Item> checkedGetSum = ItemService.getSumOfAllOrdersByUserIdAndPeriod(itemInDB.getId(),
                date.getTime(), date.getTime());
        assertNotNull(checkedGetSum);

        ItemService.delete(checkedItemInDB.getId());
        Item deletedItem = ItemService.findById(itemInDB.getId());

        assertNull(deletedItem);
    }
}