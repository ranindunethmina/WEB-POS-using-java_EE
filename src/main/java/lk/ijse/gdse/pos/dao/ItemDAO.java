package lk.ijse.gdse.pos.dao;

import lk.ijse.gdse.pos.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface ItemDAO permits ItemDAOImpl{
    String saveItem(ItemDTO item, Connection connection) throws SQLException;
    boolean deleteItem(String id, Connection connection) throws SQLException;
    boolean updateItem(String id, ItemDTO item, Connection connection) throws SQLException;
    ItemDTO getItem(String id, Connection connection) throws SQLException;
}
