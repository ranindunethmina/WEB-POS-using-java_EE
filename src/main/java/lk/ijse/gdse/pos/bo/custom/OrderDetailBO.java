package lk.ijse.gdse.pos.bo.custom;

import lk.ijse.gdse.pos.bo.SuperBO;
import lk.ijse.gdse.pos.dto.OrderDetailDTO;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailBO extends SuperBO {
    List<OrderDetailDTO> getAllOrderDetails() throws SQLException;
}
