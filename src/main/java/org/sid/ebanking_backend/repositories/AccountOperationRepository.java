package org.sid.ebanking_backend.repositories;

import org.sid.ebanking_backend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// on gère quelle entité ? AccountOperation
// on gère quel type ? Long
public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    // @TODO 6 étape 1
    // On commence les opérations
    List<AccountOperation> findByBankAccountId(String accountId);

    // @TODO 6 étape 13
    Page<AccountOperation> findPageByBankAccountId(String accountId, Pageable pageable);
}
