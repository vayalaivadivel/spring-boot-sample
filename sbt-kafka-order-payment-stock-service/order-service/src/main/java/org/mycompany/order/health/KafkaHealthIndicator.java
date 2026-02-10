package org.mycompany.order.health;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {
    private final AdminClient adminClient;

    public KafkaHealthIndicator(AdminClient adminClient) {
        this.adminClient = adminClient; // singleton
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            var clusterId = adminClient.describeCluster().clusterId().get(3, TimeUnit.SECONDS);
            var nodeCount = adminClient.describeCluster().nodes().get(3, TimeUnit.SECONDS).size();

            builder.up()
                    .withDetail("clusterId", clusterId)
                    .withDetail("nodeCount", nodeCount);
        } catch (Exception e) {
            builder.down(e);
        }
    }
}