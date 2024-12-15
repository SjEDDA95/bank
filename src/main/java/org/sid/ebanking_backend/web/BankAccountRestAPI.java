package org.sid.ebanking_backend.web;


import lombok.AllArgsConstructor;
import org.sid.ebanking_backend.dto.BankAccountDTO;
import org.sid.ebanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.ebanking_backend.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// @TODO - 4 étape 1
@RestController
@AllArgsConstructor // Injections des dépendences
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    // @TODO - 4 étape 15
    // Méthode pour ajouter un compte
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    // @TODO - 4 étape 16
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts() {
        return bankAccountService.bankAccountList();
    }
}
