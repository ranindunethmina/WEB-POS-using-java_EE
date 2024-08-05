package lk.ijse.gdse.pos.bo;

import lk.ijse.gdse.pos.dto.CustomerDTO;
import lk.ijse.gdse.pos.dto.ItemDTO;

import java.sql.Connection;

public interface ItemBO {
    String saveItem(ItemDTO item, Connection connection)throws Exception;
    boolean deleteItem(String id, Connection connection)throws Exception;
    boolean updateItem(String id, ItemDTO item, Connection connection)throws Exception;
    ItemDTO getItem(String id, Connection connection)throws Exception;
}
