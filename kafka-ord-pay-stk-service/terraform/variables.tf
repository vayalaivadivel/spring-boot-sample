############################
# Variables
############################
variable "region" {
  description = "Region where resources will be created"
  type        = string
}

variable "ec2-instance_type" {
  description = "instance type for EC2"
  type        = string
}

variable "key_name" {
  description = "EC2 key pair name"
  type        = string
}

variable "kafka_broker_count" {
  description = "Name broker count for MSK cluster"
  type        = number
}
variable "kafka_instance_type" {
  description = "Instance type for MSK brokers"
  type        = string
}

variable "rds_username" {
  description = "RDS MySQL username"
  type        = string
  default     = "admin"
}

variable "rds_password" {
  description = "RDS MySQL password"
  type        = string
  sensitive   = true
}

variable "rds_db_name" {
  description = "RDS MySQL database name"
  type        = string
  default     = "practice_db"
}

variable "environment" {
  description = "Environment name"
  type        = string
}

variable "project" {
  description = "Name of the project"
  type        = string
}
