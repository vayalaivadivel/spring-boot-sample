package org.mycompany.order.service;

import org.mycompany.core.common.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderManageService {
    public Order confirm(Order orderPayment, Order orderStock) {

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
