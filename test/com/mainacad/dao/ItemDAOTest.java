package com.mainacad.dao;

import com.mainacad.model.Item;
import org.junit.jupiter.api.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemDAOTest {


    private static List<Item> items = new ArrayList<>();
    @BeforeAll
    static void setUp() {
        Item item = new Item( "test_itemCode", "test_name",23);
        items.add(item);
    }

    @AfterAll
    static void tearDown() {
        items.stream().forEach(item -> ItemDAO.delete(item.getId()));
    }

    @Test
    void createAndFindAndDelete() {
        Date date = new Date();

        assertNull(items.get(0).getId());
        Item itemInDB = ItemDAO.create(items.get(0));

        assertNotNull(itemInDB);
        assertNotNull(itemInDB.getId());

        Item checkedItemInDB = ItemDAO.findById(itemInDB.getId());
        assertNotNull(checkedItemInDB);

        Item checkedItemInDBByLogin = ItemDAO.findByItemCode(itemInDB.getItemCode());
        assertNotNull(checkedItemInDBByLogin);

        List<Item> checkedItemInDBByAll = ItemDAO.findAll();
        assertNotNull(checkedItemInDBByAll);

        List<Item> checkedGetSum = ItemDAO.getSumOfAllOrdersByUserIdAndPeriod(itemInDB.getId(),
                date.getTime(), date.getTime());
        assertNotNull(checkedGetSum);

        ItemDAO.delete(checkedItemInDB.getId());
        Item deletedItem = ItemDAO.findById(itemInDB.getId());
        assertNull(deletedItem);
    }
}
