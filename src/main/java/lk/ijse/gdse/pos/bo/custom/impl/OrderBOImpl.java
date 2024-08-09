package lk.ijse.gdse.pos.bo.custom.impl;

import lk.ijse.gdse.pos.bo.custom.OrderBO;
import lk.ijse.gdse.pos.dao.DAOFactory;
import lk.ijse.gdse.pos.dao.custom.ItemDAO;
import lk.ijse.gdse.pos.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.pos.dto.OrderDTO;
import lk.ijse.gdse.pos.dto.OrderDetailDTO;
import lk.ijse.gdse.pos.entity.Item;
import lk.ijse.gdse.pos.entity.Order;
import lk.ijse.gdse.pos.entity.OrderDetail;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class OrderBOImpl implements OrderBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);


    @Override
    public boolean placeOrder(OrderDTO order) throws SQLException {
        try (Connection connection = SQLUtil.getConnection()) {
            //Set Connections
            connection.setAutoCommit(false);
            orderDAO.setConnection(connection);
            orderDetailDAO.setConnection(connection);
            itemDAO.setConnection(connection);
            //Save In the Order Table
            if (saveOrder(order) && saveOrderDetails(order.getOrderId(), order.getOrderDetails()) && updateItems(order.getOrderDetails())) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);
            return false;
        }
    }

    private boolean updateItems(List<OrderDetailDTO> orderDetails) throws SQLException {
        for (OrderDetailDTO orderDetail : orderDetails) {
            Item item = itemDAO.search(orderDetail.getItemId());
            item.setQuantity(item.getQuantity() - orderDetail.getQuantity());
            boolean isItemUpdated = itemDAO.update(item.getItemId(), item);
            if (!isItemUpdated) {
                return false;
            }
        }
        return true;
    }
    private boolean saveOrderDetails(String orderId, List<OrderDetailDTO> orderDetails) throws SQLException {
        for (OrderDetailDTO orderDetailDTO : orderDetails) {
            boolean isSaved = orderDetailDAO.save(new OrderDetail(
                    orderId,
                    orderDetailDTO.getItemId(),
                    orderDetailDTO.getQuantity(),
                    orderDetailDTO.getPrice()
            ));
            if (!isSaved) {
                return false;
            }
        }
        return true;
    }
    private boolean saveOrder(OrderDTO order) throws SQLException {
        return orderDAO.save(new Order(
                order.getOrderId(),
                LocalDateTime.now(),
                order.getCustomerId(),
                order.getSubtotal(),
                order.getDiscount(),
                order.getAmount_payed()
        ));
    }

    @Override
    public String getOrderId() throws SQLException {
        try (Connection connection = SQLUtil.getConnection()) {
            orderDAO.setConnection(connection);
            String lastOrderId = orderDAO.getId();
            if (lastOrderId != null){
                String prefix = lastOrderId.substring(0, 1);
                int number = Integer.parseInt(lastOrderId.substring(1));
                number++;
                String formattedNumber = String.format("%03d", number);
                return prefix + formattedNumber;
            }
            return "O001";
        }
    }
}
