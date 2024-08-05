package lk.ijse.gdse.pos.dao;

import lk.ijse.gdse.pos.dto.CustomerDTO;

import java.sql.Connection;
import java.sql.SQLException;

public final class CustomerDAOImpl implements CustomerDAO {
    public static String SAVE_CUSTOMER = "INSERT INTO customer (c_id, name, address, mobile) VALUES(?,?,?,?)";
    public static String GET_CUSTOMER = "SELECT * FROM customer WHERE c_id=?";
    public static String UPDATE_CUSTOMER = "UPDATE customer SET name=?, address=?, mobile=? WHERE c_id=?";
    public static String DELETE_CUSTOMER = "DELETE FROM customer WHERE c_id=?";

    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(SAVE_CUSTOMER);
            ps.setString(1,customer.getId());
            ps.setString(2,customer.getName());
            ps.setString(3,customer.getAddress());
            ps.setInt(4,customer.getMobile_no());
            if (ps.executeUpdate() != 0) {
                return ("Save Customer Successfully");
            } else {
                return ("Failed to Save Customer");
            }
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws SQLException {
        var ps = connection.prepareStatement(DELETE_CUSTOMER);
        ps.setString(1, id);
        return ps.executeUpdate() != 0;
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws SQLException {
        try {
            var ps = connection.prepareStatement(UPDATE_CUSTOMER);
            ps.setString(1,customer.getName());
            ps.setString(2,customer.getAddress());
            ps.setInt(3,customer.getMobile_no());
            ps.setString(4,id);
            return ps.executeUpdate() != 0;
        }catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws SQLException {
        try {
            CustomerDTO customerDTO = new CustomerDTO();
            var ps = connection.prepareStatement(GET_CUSTOMER);
            ps.setString(1, id);
            var rst = ps.executeQuery();
            while (rst.next()) {
                customerDTO.setId(rst.getString("id"));
                customerDTO.setName(rst.getString("name"));
                customerDTO.setAddress(rst.getString("address"));
                customerDTO.setMobile_no(rst.getInt("mobile_no"));
            }
            return customerDTO;
        } catch (Exception e){
            throw new SQLException(e.getMessage());
        }
    }
}
