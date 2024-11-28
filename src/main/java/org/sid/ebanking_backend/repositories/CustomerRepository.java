package org.sid.ebanking_backend.repositories;

import org.sid.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// on gère quelle entité ? Customer
// on gère quel type ? Long
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
