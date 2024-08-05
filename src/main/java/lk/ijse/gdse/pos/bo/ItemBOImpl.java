package lk.ijse.gdse.pos.bo;

import lk.ijse.gdse.pos.dao.ItemDAOImpl;
import lk.ijse.gdse.pos.dto.ItemDTO;

import java.sql.Connection;

public class ItemBOImpl implements ItemBO{
    @Override
    public String saveItem(ItemDTO item, Connection connection) throws Exception {
        var itemDAOIMPL = new ItemDAOImpl();
        return itemDAOIMPL.saveItem(item, connection);
    }

    @Override
    public boolean deleteItem(String id, Connection connection) throws Exception {
        var itemDAOIMPL = new ItemDAOImpl();
        return itemDAOIMPL.deleteItem(id, connection);
    }

    @Override
    public boolean updateItem(String id, ItemDTO item, Connection connection) throws Exception {
        var itemDAOIMPL = new ItemDAOImpl();
        return itemDAOIMPL.updateItem(id, item, connection);
    }

    @Override
    public ItemDTO getItem(String id, Connection connection) throws Exception {
        var itemDAOIMPL = new ItemDAOImpl();
        return itemDAOIMPL.getItem(id, connection);
    }
}
