package org.mycompany.stock;

import jakarta.annotation.PostConstruct;
import org.mycompany.core.common.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.mycompany.stock.domain.Product;
import org.mycompany.stock.repository.ProductRepository;
import org.mycompany.stock.service.OrderManageService;

import java.util.Random;

@SpringBootApplication
@EnableKafka
public class StockApp {

    private static final Logger LOG = LoggerFactory.getLogger(StockApp.class);

    public static void main(String[] args) {
        SpringApplication.run(StockApp.class, args);
    }

    @Autowired
    OrderManageService orderManageService;

    @KafkaListener(id = "orders", topics = "orders", groupId = "stock")
    public void onEvent(Order o) {
        LOG.info("Received: {}" , o);
        if (o.status().equals("NEW"))
            orderManageService.reserve(o);
        else
            orderManageService.confirm(o);
    }

    @Autowired
    private ProductRepository repository;

    @PostConstruct
    public void generateData() {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            int count = r.nextInt(10, 1000);
            Product p = new Product(null, "Product" + i, count, 0);
            repository.save(p);
        }
    }
}
