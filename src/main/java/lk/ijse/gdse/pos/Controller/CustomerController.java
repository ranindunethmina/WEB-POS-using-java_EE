package lk.ijse.gdse.pos.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.pos.bo.CustomerBOImpl;
import lk.ijse.gdse.pos.dto.CustomerDTO;
import lk.ijse.gdse.pos.util.Util;
import org.slf4j.LoggerFactory;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {
    static org.slf4j.Logger logger = LoggerFactory.getLogger(CustomerController.class.getName());
    Connection connection;

    @Override
    public void init() throws ServletException {
        logger.info("Init method invoked");
        try {
            var ctx = new InitialContext();
            DataSource pool = (DataSource) ctx.lookup("java:comp/env/jdbc/stuReg");
            this.connection = pool.getConnection();
            logger.debug("Connection initialized",this.connection);

        } catch (SQLException | NamingException e) {
            logger.error("DB connection not init" );
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()){
            var customerBOIMPL = new CustomerBOImpl();
            Jsonb jsonb = JsonbBuilder.create();
            var custId = req.getParameter("custId");
            resp.setContentType("application/json");
            jsonb.toJson(customerBOIMPL.getCustomer(custId,connection),writer);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")){
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            logger.error("Request not matched with the criteria");
        }
        try (var writer = resp.getWriter()){
            Jsonb jsonb = JsonbBuilder.create();
            var customerBOIMPL = new CustomerBOImpl();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            logger.info("Invoke idGenerate()");
            customer.setId(Util.idGenerate());
            //Save data in the DB
            writer.write(customerBOIMPL.saveCustomer(customer,connection));
            logger.info("Customer saved successfully");
            resp.setStatus(HttpServletResponse.SC_CREATED);
        }catch (Exception e){
            logger.error("Connection failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()) {
            var custId = req.getParameter("custId");
            var customerBOIMPL = new CustomerBOImpl();
            if(customerBOIMPL.deleteCustomer(custId,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Delete failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var writer = resp.getWriter()){
            var customerBOIMPL = new CustomerBOImpl();
            var custId = req.getParameter("custId");
            Jsonb jsonb = JsonbBuilder.create();
            CustomerDTO customer = jsonb.fromJson(req.getReader(), CustomerDTO.class);
            if(customerBOIMPL.updateCustomer(custId, customer,connection)){
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }else {
                writer.write("Update failed");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
