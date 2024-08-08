package lk.ijse.gdse.pos.dao.custom.impl;

import lk.ijse.gdse.pos.dao.custom.ItemDAO;
import lk.ijse.gdse.pos.entity.Item;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAOImpl implements ItemDAO {
    private Connection connection;

    @Override
    public List<Item> getAll() throws SQLException {
        ResultSet result=  SQLUtil.execute(connection,"SELECT * FROM item");
        List<Item> items = new ArrayList<>();
        while (result.next()){
            items.add(new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getString(6)
            ));
        }
        return items;
    }

    @Override
    public boolean save(Item item) throws SQLException {
        return SQLUtil.execute(connection,"INSERT INTO item VALUES(?,?,?,?,?,?)",
                item.getItemId(),
                item.getItemName(),
                item.getPrice(),
                item.getQuantity(),
                item.getCategory(),
                item.getImagePath()
        );
    }

    @Override
    public boolean update(String id, Item item) throws SQLException {
        return SQLUtil.execute(connection,"UPDATE item SET i_Id=?, itemName=?, price=?, quantity=?, category=?, imagePath=? WHERE i_Id=?",
                item.getItemId(),
                item.getItemName(),
                item.getPrice(),
                item.getQuantity(),
                item.getCategory(),
                item.getImagePath(),
                id
        );
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(connection,"DELETE FROM item WHERE i_Id=?", id);
    }

    @Override
    public Item search(String id) throws SQLException {
        ResultSet result =  SQLUtil.execute(connection,"SELECT * FROM item WHERE i_Id=?", id);
        if (result.next()){
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4),
                    result.getString(5),
                    result.getString(6)
            );
        }
        return null;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}