package org.mycompany.order.service;

import org.mycompany.core.common.Order;
import org.mycompany.order.controller.OrderController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderManageService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderManageService.class);

    public Order confirm(Order orderPayment, Order orderStock) {
        LOG.info("Order payment status: {}", orderPayment.status());
        if (orderPayment.status().equals("ACCEPT") &&
                orderStock.status().equals("ACCEPT")) {
            return new Order(orderPayment.id(),
                    orderPayment.id(),
                    orderPayment.productId(),
                    orderPayment.productCount(),
                    orderPayment.price(), "CONFIRMED");
        } else if (orderPayment.status().equals("REJECT") &&
                orderStock.status().equals("REJECT")) {
            return new Order(orderPayment.id(),
                    orderPayment.id(),
                    orderPayment.productId(),
                    orderPayment.productCount(),
                    orderPayment.price(), "REJECTED");
        } else if (orderPayment.status().equals("REJECT") ||
                orderStock.status().equals("REJECT")) {
            return new Order(orderPayment.id(),
                    orderPayment.id(),
                    orderPayment.productId(),
                    orderPayment.productCount(),
                    orderPayment.price(), "ROLLBACK", orderPayment.status().equals("REJECT")
                    ? "PAYMENT" : "STOCK");

        } else {
            return new Order(orderPayment.id(),
                    orderPayment.id(),
                    orderPayment.productId(),
                    orderPayment.productCount(),
                    orderPayment.price());
        }
    }
}
