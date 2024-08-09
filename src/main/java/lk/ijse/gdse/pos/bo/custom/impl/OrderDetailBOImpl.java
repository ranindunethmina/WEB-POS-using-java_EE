package lk.ijse.gdse.pos.bo.custom.impl;

import lk.ijse.gdse.pos.bo.custom.OrderDetailBO;
import lk.ijse.gdse.pos.dao.DAOFactory;
import lk.ijse.gdse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.pos.dto.OrderDetailDTO;
import lk.ijse.gdse.pos.entity.OrderDetail;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailBOImpl implements OrderDetailBO {
    private final OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);

    @Override
    public List<OrderDetailDTO> getAllOrderDetails() throws SQLException {
        try (Connection connection = SQLUtil.getConnection()) {
            orderDetailDAO.setConnection(connection);
            List<OrderDetail> orderDetailList = orderDetailDAO.getAll();
            List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

            for (OrderDetail orderDetail : orderDetailList) {
                orderDetailDTOList.add(new OrderDetailDTO(
                        orderDetail.getOrderId(),
                        orderDetail.getItemId(),
                        orderDetail.getQuantity(),
                        orderDetail.getUntPrice()
                ));
            }
            return orderDetailDTOList;
        }
    }
}
