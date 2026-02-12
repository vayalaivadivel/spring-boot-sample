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

variable "profile" {
  description = "Name of the profile"
  type        = string
  default     = "devops"
}

variable "kafka_broker_count" {
  description = "Name broker count for MSK cluster"
  type        = number
}
variable "kafka_instance_type" {
  description = "Instance type for MSK brokers"
  type        = string
}