package lk.ijse.gdse.pos.dao;

import lk.ijse.gdse.pos.dto.ItemDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class ItemDAOImpl implements ItemDAO {
    public static String SAVE_ITEM = "INSERT INTO Item (i_id, name, price, qty, category, image) VALUES(?,?,?,?,?,?)";
    public static String GET_ITEM = "SELECT * FROM Item WHERE i_id=?";
    public static String UPDATE_ITEM = "UPDATE Item SET name=?, price=?, qty=?, category=?, image=? WHERE i_id=?";
    public static String DELETE_ITEM = "DELETE FROM Item WHERE i_id=?";

    @Override
    public String saveItem(ItemDTO item, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(SAVE_ITEM);
            ps.setString(1,item.getId());
            ps.setString(2,item.getName());
            ps.setInt(3,item.getPrice());
            ps.setInt(4,item.getQty());
            ps.setString(5,item.getCategory());
            ps.setString(6,item.getImage());
            if (ps.executeUpdate() != 0) {
                return ("Save Item Successfully");
            } else {
                return ("Failed to Save Item");
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws SQLException {
        var ps = connection.prepareStatement(DELETE_ITEM);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateItem(String id, ItemDTO item, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_ITEM);
            ps.setString(1,item.getName());
            ps.setInt(2,item.getPrice());
            ps.setInt(3,item.getQty());
            ps.setString(4,item.getCategory());
            ps.setString(5,item.getImage());
            ps.setString(6,id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws SQLException {
        try {
            ItemDTO itemDTO = new ItemDTO();
            var ps = connection.prepareStatement(GET_ITEM);
            ps.setString(1, id);
            var rst = ps.executeQuery();
            while (rst.next()) {
                itemDTO.setId(rst.getString("i_id"));
                itemDTO.setName(rst.getString("name"));
                itemDTO.setPrice(rst.getInt("price"));
                itemDTO.setQty(rst.getInt("qty"));
                itemDTO.setCategory(rst.getString("category"));
                itemDTO.setImage(rst.getString("image"));
            }
            return itemDTO;
        } catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
