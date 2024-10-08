package lk.ijse.gdse.pos.Controller;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.gdse.pos.bo.BOFactory;
import lk.ijse.gdse.pos.bo.custom.OrderBO;
import lk.ijse.gdse.pos.dto.OrderDTO;
import lk.ijse.gdse.pos.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/order")
public class OrderController extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final OrderBO orderBO = (OrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ORDER);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Received POST request for order creation");
        try (PrintWriter writer = resp.getWriter()) {
            if (req.getContentType() == null || !req.getContentType().toLowerCase().startsWith("application/json")) {
                logger.warn("Invalid content type received: {}", req.getContentType());
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Expected application/json content type");
                return;
            }

            Jsonb jsonb = JsonbBuilder.create();
            OrderDTO order = jsonb.fromJson(req.getReader(), OrderDTO.class);
            logger.debug("Attempting to validate order");

            // Validate order
            List<String> validationErrors = ValidationUtil.validateOrder(order);
            if (!validationErrors.isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Validation failed: " + String.join(", ", validationErrors));
                return;
            }

            boolean isSaved = orderBO.placeOrder(order);
            if (isSaved) {
                logger.info("Order saved successfully: {}", order.getOrderId());
                resp.setStatus(HttpServletResponse.SC_CREATED);
                writer.write("Order Saved Successfully");
            } else {
                logger.warn("Failed to save order: {}", order.getOrderId());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                writer.write("Failed to Save Order");
            }
        } catch (SQLException | IOException e) {
            logger.error("Error while processing order creation request", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.debug("Received GET request for order ID");
        try (PrintWriter writer = resp.getWriter()) {
            String orderId = orderBO.getOrderId();
            logger.debug("Retrieved order ID: {}", orderId);

            Map<String, String> response = new HashMap<>();
            response.put("orderId", orderId);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");

            Jsonb jsonb = JsonbBuilder.create();
            jsonb.toJson(response, writer);

            logger.info("Successfully returned order ID");
        } catch (SQLException | IOException e) {
            logger.error("Error while retrieving order ID", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
