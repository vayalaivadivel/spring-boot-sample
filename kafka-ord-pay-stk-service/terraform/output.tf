############################
# Outputs
############################
output "msk_cluster_arn" {
  value = aws_msk_cluster.kafka.arn
}
output "ec2_public_ip" {
  value       = aws_instance.ec2.public_ip
  description = "Public IP of EC2 instance"
}

output "kafka_bootstrap_brokers_tls" {
  value       = aws_msk_cluster.kafka.bootstrap_brokers_tls
  description = "Kafka bootstrap brokers (TLS)"
}
output "kafka_bootstrap_brokers" {
  description = "Kafka bootstrap brokers"
  value = aws_msk_cluster.kafka.bootstrap_brokers
}

############################
# Terraform Outputs for RDS
############################
output "rds_endpoint" {
  description = "RDS MySQL endpoint"
  value       = aws_db_instance.mysql.endpoint
}

output "rds_username" {
  description = "RDS MySQL username"
  value       = aws_db_instance.mysql.username
}

output "rds_password" {
  description = "RDS MySQL password"
  value       = aws_db_instance.mysql.password
  sensitive   = true
}
