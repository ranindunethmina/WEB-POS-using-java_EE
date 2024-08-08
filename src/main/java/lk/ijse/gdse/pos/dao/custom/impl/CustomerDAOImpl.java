package lk.ijse.gdse.pos.dao.custom.impl;

import lk.ijse.gdse.pos.dao.custom.CustomerDAO;
import lk.ijse.gdse.pos.entity.Customer;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class CustomerDAOImpl implements CustomerDAO{
    private Connection connection;

    @Override
    public List<Customer> getAll() throws SQLException {
        ResultSet resultSet = SQLUtil.execute(connection, "SELECT * FROM customer");
        ArrayList<Customer> customers = new ArrayList<>();
        while (resultSet.next()) {
            customers.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return customers;
    }

    @Override
    public boolean save(Customer customer) throws SQLException {
        return SQLUtil.execute(connection, "INSERT INTO customer VALUES(?,?,?,?)",
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getMobile());
    }

    @Override
    public boolean update(String id, Customer customer) throws SQLException {
        return SQLUtil.execute(connection, "UPDATE customer SET c_Id=?, firstName=?, address=?, mobile=? WHERE c_Id=?",
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getMobile(),
                id);
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute(connection, "DELETE FROM customer WHERE c_Id=?", id);
    }

    @Override
    public Customer search(String id) throws SQLException {
        return null;
    }

    @Override
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
