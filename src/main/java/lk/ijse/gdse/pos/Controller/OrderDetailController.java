package lk.ijse.gdse.pos.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.pos.bo.BOFactory;
import lk.ijse.gdse.pos.bo.custom.OrderDetailBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/orderDetail")
public class OrderDetailController extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
    private final OrderDetailBO orderDetailBO = (OrderDetailBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER_DETAIL);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("Received GET request for order details");
        try (PrintWriter writer = resp.getWriter()) {
            Jsonb jsonb = JsonbBuilder.create();
            resp.setContentType("application/json");
            jsonb.toJson(orderDetailBO.getAllOrderDetails(), writer);
            logger.info("Successfully retrieved all order details");
        } catch (SQLException | IOException e) {
            logger.error("Error retrieving  order details", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
