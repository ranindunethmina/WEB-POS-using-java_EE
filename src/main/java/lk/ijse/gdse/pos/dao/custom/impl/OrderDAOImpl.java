package lk.ijse.gdse.pos.dao.custom.impl;

import lk.ijse.gdse.pos.dao.custom.OrderDAO;
import lk.ijse.gdse.pos.entity.Order;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDAOImpl implements OrderDAO {
    private Connection connection;


    @Override
    public List<Order> getAll() throws SQLException {
        return null;
    }

    @Override
    public boolean save(Order order) throws SQLException {
        return SQLUtil.execute(connection, "INSERT INTO orders VALUES(?,?,?,?,?,?)",
                order.getOrderId(),
                order.getDateAndTime(),
                order.getCustomerId(),
                order.getSubtotal(),
                order.getDiscount(),
                order.getAmount_payed()
        );
    }

    @Override
    public boolean update(String id, Order order) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return false;
    }

    @Override
    public Order search(String id) throws SQLException {
        return null;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String getId() throws SQLException {
        ResultSet resultSet = SQLUtil.execute(connection,"SELECT orderId FROM orders ORDER BY orderId DESC LIMIT 1");
        if(resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
