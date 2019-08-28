package com.mainacad.dao;

import com.mainacad.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public static Item create(Item item){

        String sql = "INSERT INTO items(item_code, name, price) " +
                "VALUES(?,?,?)";
        String sequenceSQL = "SELECT currval(pg_get_serial_sequence('items','id'))";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             PreparedStatement sequenceStatement = connection.prepareStatement(sequenceSQL)) {

            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getPrice());

            preparedStatement.executeUpdate();

            ResultSet resultSet = sequenceStatement.executeQuery();

            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                item.setId(id);
                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Item update(Item item){

        String sql = "UPDATE items SET item_code=?, name=?, price=? WHERE id=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {

            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setString(2, item.getName());
            preparedStatement.setInt(3, item.getPrice());
            preparedStatement.setInt(4, item.getId());

            preparedStatement.executeUpdate();
            return item;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Item> findAll(){

        String sql = "SELECT * FROM items";

        try(Connection connection = ConnectionToDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            ResultSet resultSet = preparedStatement.executeQuery();
            ArrayList<Item> items = new ArrayList<>();

            while (resultSet.next()){
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setItemCode(resultSet.getString("item_code"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getInt("price"));

                items.add(item);
                return items;
            }
        } catch (SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    public static Item findById(Integer id){

        String sql = "SELECT * FROM items WHERE id=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setItemCode(resultSet.getString("item_code"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getInt("price"));

                return item;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Item findByItemCode(String itemCode){

        String sql = "SELECT * FROM items WHERE item_code=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1,itemCode);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Item item = new Item();
                item.setId(resultSet.getInt("id"));
                item.setItemCode(resultSet.getString("item_code"));
                item.setName(resultSet.getString("name"));
                item.setPrice(resultSet.getInt("price"));

                return item;
            }
        } catch (SQLException e){
            e.getStackTrace();
        }
        return null;
    }

    public static void delete(Integer id){

        String sql = "DELETE FROM items WHERE id=?";

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getSumOfAllOrdersByUserIdAndPeriod(Integer userId, Long from, Long to){
        String sql = "SELECT SUM(i.price*o.amount) " + "FROM items i " +
                "JOIN orders o ON o.item_id = i.id " +
                "JOIN carts c ON o.cart_id = c.id " +
                "WHERE c.user_id=2 AND " +
                "c.creation_time>1564088300000 AND " +
                "c.creation_time<1564088500000 AND " +
                "c.closed=true";

        List<Item> items = new ArrayList<>();

        try (Connection connection = ConnectionToDB.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setLong(2, from);
            preparedStatement.setLong(3, to);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Item item = new Item();

                item.setPrice(resultSet.getInt("price"));
                items.add(item);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }
}
