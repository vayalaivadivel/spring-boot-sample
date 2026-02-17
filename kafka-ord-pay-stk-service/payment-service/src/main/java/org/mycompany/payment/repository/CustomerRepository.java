package org.mycompany.payment.repository;

import org.springframework.data.repository.CrudRepository;
import org.mycompany.payment.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
