package org.sid.ebanking_backend.service;

import jakarta.transaction.Transactional;
import org.sid.ebanking_backend.entities.BankAccount;
import org.sid.ebanking_backend.entities.CurrentAccount;
import org.sid.ebanking_backend.entities.SavingAccount;
import org.sid.ebanking_backend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consult() {
        BankAccount bankAccount =
                bankAccountRepository.findById("2b46295b-264b-4b83-a9ff-648741445cb4").orElse(null);
        if(bankAccount != null) {
            System.out.println("JE SUIS LE COMPTE !!!!!!!!!!!!!!");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getAccountStatus());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getCustomer().getCustomerName());

            if (bankAccount instanceof CurrentAccount) {
                double currentAccountCast;
                currentAccountCast = ((CurrentAccount) bankAccount).getOverDraft();
                System.out.println("OVERDRAFT !!!!!!!!!!!!!!!");
                System.out.println(currentAccountCast);
            } else if (bankAccount instanceof SavingAccount) {
                double savingAccountCast;
                savingAccountCast = ((SavingAccount) bankAccount).getInterestRate();
                System.out.println("RATE !!!!!!!!!!!!!!!");
                System.out.println(savingAccountCast);
            }

            bankAccount.getAccountOperations().forEach(operation -> {
                System.out.println(operation.getAmount() + "\t"
                        + operation.getOperationType() + "\t"
                        + operation.getOperationDate());
            });
        }
    }
}
