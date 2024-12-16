package org.sid.ebanking_backend.repositories;

import org.sid.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// on gère quelle entité ? Customer
// on gère quel type ? Long
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("select c from Customer c where c.customerName like :kw")
    List<Customer> searchCustomer(@Param("kw") String keyword);
}
