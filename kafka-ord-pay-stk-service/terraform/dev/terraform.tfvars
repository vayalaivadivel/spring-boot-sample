region            = "us-east-1"
key_name          = "common-key"
ec2-instance_type = "t3.medium"
# kafka_cluster_name  = "dev-spring-kafka"
kafka_broker_count  = 2
kafka_instance_type = "kafka.m5.large"

rds_username = "admin"
rds_password = "Admin12345!"
rds_db_name  = "practice_db"
environment  = "dev"
project="kafka-practice"