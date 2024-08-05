package lk.ijse.gdse.pos.bo;

import lk.ijse.gdse.pos.dao.CustomerDAOImpl;
import lk.ijse.gdse.pos.dto.CustomerDTO;

import java.sql.Connection;

public class CustomerBOImpl implements CustomerBO {
    @Override
    public String saveCustomer(CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOImpl();
        return customerDAOIMPL.saveCustomer(customer, connection);
    }

    @Override
    public boolean deleteCustomer(String id, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOImpl();
        return customerDAOIMPL.deleteCustomer(id, connection);
    }

    @Override
    public boolean updateCustomer(String id, CustomerDTO customer, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOImpl();
        return customerDAOIMPL.updateCustomer(id, customer, connection);
    }

    @Override
    public CustomerDTO getCustomer(String id, Connection connection) throws Exception {
        var customerDAOIMPL = new CustomerDAOImpl();
        return customerDAOIMPL.getCustomer(id, connection);
    }
}
