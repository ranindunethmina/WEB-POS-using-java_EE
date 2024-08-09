package lk.ijse.gdse.pos.bo.custom;

import lk.ijse.gdse.pos.bo.SuperBO;
import lk.ijse.gdse.pos.dto.OrderDTO;

import java.sql.SQLException;

public interface OrderBO extends SuperBO {
    boolean placeOrder(OrderDTO order) throws SQLException;
    String getOrderId() throws SQLException;
}
