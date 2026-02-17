package pl.piomin.stock;

import org.mycompany.stock.StockApp;
import org.springframework.boot.SpringApplication;

public class StockAppTest {

    public static void main(String[] args) {
        SpringApplication.from(StockApp::main)
                .with(KafkaContainerDevMode.class)
                .run(args);
    }

}
