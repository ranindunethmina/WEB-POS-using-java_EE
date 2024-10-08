package lk.ijse.gdse.pos.dao.custom.impl;

import lk.ijse.gdse.pos.dao.custom.OrderDetailDAO;
import lk.ijse.gdse.pos.entity.OrderDetail;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    private Connection connection;

    @Override
    public List<OrderDetail> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute(connection, "SELECT * FROM orderDetail");
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (resultSet.next()) {
            orderDetails.add(new OrderDetail(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getDouble(4)
            ));
        }
        return orderDetails;    }

    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException {
        return SQLUtil.execute(connection,"INSERT INTO orderDetail VALUES(?,?,?,?)",
                orderDetail.getOrderId(),
                orderDetail.getItemId(),
                orderDetail.getQuantity(),
                orderDetail.getUntPrice()
        );
    }

    @Override
    public boolean update(String id, OrderDetail orderDetail) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public OrderDetail search(String id) throws SQLException {
        return null;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection=connection;
    }
}
