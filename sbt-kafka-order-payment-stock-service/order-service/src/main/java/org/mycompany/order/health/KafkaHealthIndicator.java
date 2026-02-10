package org.mycompany.order.health;


import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterOptions;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

@Component
public class KafkaHealthIndicator extends AbstractHealthIndicator {

    private final AdminClient adminClient;

    public KafkaHealthIndicator(KafkaAdmin kafkaAdmin) {
        this.adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        // We describe the cluster with a short timeout to check connectivity
        var cluster = adminClient.describeCluster(new DescribeClusterOptions().timeoutMs(3000));
        var clusterId = cluster.clusterId().get();
        var nodeCount = cluster.nodes().get().size();

        builder.up()
                .withDetail("clusterId", clusterId)
                .withDetail("nodeCount", nodeCount);
    }
}

