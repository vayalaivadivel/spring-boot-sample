package org.mycompany.order.health;

import jakarta.annotation.PostConstruct;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {
    @Autowired
    private KafkaAdmin kafkaAdmin;

    private AdminClient adminClient;

    @PostConstruct
    public void init() {
        // Use Spring Boot singleton AdminClient
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) {
        try {
            // Only fetch minimal metadata: cluster ID and node list
            var clusterIdFuture = adminClient.describeCluster().clusterId();
            var nodesFuture = adminClient.describeCluster().nodes();

            String clusterId = clusterIdFuture.get(3, TimeUnit.SECONDS);
            int nodeCount = nodesFuture.get(3, TimeUnit.SECONDS).size();

            builder.up()
                    .withDetail("clusterId", clusterId)
                    .withDetail("nodeCount", nodeCount);

        } catch (KafkaException ke) {
            builder.down(ke).withDetail("reason", "KafkaException: AdminClient may be unreachable");
        } catch (java.util.concurrent.TimeoutException te) {
            builder.down(te).withDetail("reason", "Timeout while connecting to Kafka cluster");
        } catch (Exception e) {
            builder.down(e).withDetail("reason", "Unexpected error in Kafka health check");
        }
    }
}