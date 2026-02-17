package org.mycompany.payment.service;

import org.mycompany.core.common.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.mycompany.payment.domain.Customer;
import org.mycompany.payment.repository.CustomerRepository;

@Service
public class OrderManageService {

    private static final String SOURCE = "payment";
    private static final Logger LOG = LoggerFactory.getLogger(OrderManageService.class);
    private CustomerRepository repository;
    private KafkaTemplate<Long, Order> template;

    public OrderManageService(CustomerRepository repository, KafkaTemplate<Long, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    public void reserve(Order order) {
        Customer customer = repository.findById(order.customerId()).orElseThrow();
        LOG.info("Found: {}", customer);
        String status=order.status();
        if (order.price() < customer.getAmountAvailable()) {
            status="ACCEPT";
            customer.setAmountReserved(customer.getAmountReserved() + order.price());
            customer.setAmountAvailable(customer.getAmountAvailable() - order.price());
        } else {
            status="REJECT";
        }

        order=new Order(order.id(), order.customerId(), order.productId(), order.productCount(), order.price(), status, SOURCE);
        repository.save(customer);
        template.send("payment-orders", order.id(), order);
        LOG.info("Sent: {}", order);
    }

    public void confirm(Order order) {
        Customer customer = repository.findById(order.customerId()).orElseThrow();
        LOG.info("Found: {}", customer);
        if (order.status().equals("CONFIRMED")) {
            customer.setAmountReserved(customer.getAmountReserved() - order.price());
            repository.save(customer);
        } else if (order.status().equals("ROLLBACK") && !order.source().equals(SOURCE)) {
            customer.setAmountReserved(customer.getAmountReserved() - order.price());
            customer.setAmountAvailable(customer.getAmountAvailable() + order.price());
            repository.save(customer);
        }

    }
}
