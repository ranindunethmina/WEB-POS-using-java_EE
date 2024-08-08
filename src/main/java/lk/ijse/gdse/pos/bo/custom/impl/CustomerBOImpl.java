package lk.ijse.gdse.pos.bo.custom.impl;

import lk.ijse.gdse.pos.bo.custom.CustomerBO;
import lk.ijse.gdse.pos.dao.DAOFactory;
import lk.ijse.gdse.pos.dao.custom.CustomerDAO;
import lk.ijse.gdse.pos.dto.CustomerDTO;
import lk.ijse.gdse.pos.entity.Customer;
import lk.ijse.gdse.pos.util.SQLUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBOImpl implements CustomerBO {
    private final CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        try(Connection connection = SQLUtil.getConnection()) {
            customerDAO.setConnection(connection);
            return customerDAO.save(new Customer(
                    customerDTO.getId(),
                    customerDTO.getName(),
                    customerDTO.getAddress(),
                    customerDTO.getMobile()
            ));
        }
    }

    @Override
    public boolean deleteCustomer(String customerId) throws SQLException {
        try(Connection connection = SQLUtil.getConnection()) {
            customerDAO.setConnection(connection);
            return customerDAO.delete(customerId);
        }
    }

    @Override
    public boolean updateCustomer(String customerId, CustomerDTO customerDTO) throws SQLException {
        try (Connection connection = SQLUtil.getConnection()) {
            customerDAO.setConnection(connection);
            return customerDAO.update(customerId,
                    new Customer(customerDTO.getId(),
                            customerDTO.getName(),
                            customerDTO.getAddress(),
                            customerDTO.getMobile()
                    ));
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException {
        try (Connection connection = SQLUtil.getConnection()) {
            customerDAO.setConnection(connection);
            List<Customer> allCustomers = customerDAO.getAll();
            List<CustomerDTO> allCustomersDTO = new ArrayList<>();
            for (Customer customer : allCustomers) {
                allCustomersDTO.add(new CustomerDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getMobile()
                ));
            }
            return allCustomersDTO;
        }
    }
}
