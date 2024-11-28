package org.sid.ebanking_backend.repositories;

import org.sid.ebanking_backend.entities.BankAccount;
import org.sid.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

// on gère quelle entité ? BankAccount
// on gère quel type ? String
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
