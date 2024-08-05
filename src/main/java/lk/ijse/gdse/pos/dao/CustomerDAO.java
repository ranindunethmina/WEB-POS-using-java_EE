package lk.ijse.gdse.pos.dao;

import lk.ijse.gdse.pos.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public sealed interface CustomerDAO permits CustomerDAOImpl{
    String saveCustomer(CustomerDTO customer, Connection connection) throws SQLException;
    boolean deleteCustomer(String id, Connection connection) throws SQLException;
    boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws SQLException;
    CustomerDTO getCustomer(String id, Connection connection) throws SQLException;
}
