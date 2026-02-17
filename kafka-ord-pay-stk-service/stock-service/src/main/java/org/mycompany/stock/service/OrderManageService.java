package org.mycompany.stock.service;

import org.mycompany.core.common.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.mycompany.stock.domain.Product;
import org.mycompany.stock.repository.ProductRepository;

@Service
public class OrderManageService {

    private static final String SOURCE = "stock";
    private static final Logger LOG = LoggerFactory.getLogger(OrderManageService.class);
    private ProductRepository repository;
    private KafkaTemplate<Long, Order> template;

    public OrderManageService(ProductRepository repository, KafkaTemplate<Long, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    public void reserve(Order order) {
        Product product = repository.findById(order.productId()).orElseThrow();
        LOG.info("Found: {}", product);
        String status=order.status();
        if (order.status().equals("NEW")) {
            if (order.productCount() < product.getAvailableItems()) {
                product.setReservedItems(product.getReservedItems() + order.productCount());
                product.setAvailableItems(product.getAvailableItems() - order.productCount());
               status="ACCEPT";
                repository.save(product);
            } else {
                status="REJECT";
            }
            order=new Order(order.id(), order.customerId(), order.productId(), order.productCount(), order.price(), status, SOURCE);

            template.send("stock-orders", order.id(), order);
            LOG.info("Sent: {}", order);
        }
    }

    public void confirm(Order order) {
        Product product = repository.findById(order.productId()).orElseThrow();
        LOG.info("Found: {}", product);
        if (order.status().equals("CONFIRMED")) {
            product.setReservedItems(product.getReservedItems() - order.productCount());
            repository.save(product);
        } else if (order.status().equals("ROLLBACK") && !order.source().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() - order.productCount());
            product.setAvailableItems(product.getAvailableItems() + order.productCount());
            repository.save(product);
        }
    }

}
