package org.sid.ebanking_backend.web;


import lombok.AllArgsConstructor;
import org.sid.ebanking_backend.dto.*;
import org.sid.ebanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.ebanking_backend.exceptions.InsufficientBalanceException;
import org.sid.ebanking_backend.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @TODO - 4 étape 1
@RestController
@AllArgsConstructor // Injections des dépendences
@CrossOrigin("*")
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

    // @TODO 6 étape 6, consulter une liste d'opérations d'un compte
    @GetMapping("/accounts/{accountId}/operations")
    // possibilité d'omettre @PathVariable si le string accountId
    public List<AccountOperationDTO> getListOfOperations(@PathVariable String accountId) {
        return bankAccountService.accountHistoryOperations(accountId);
    }

    // @TODO 6 étape 10
    @GetMapping("/accounts/{accountId}/pageOperations")
    // quand on fait la pagination, on passe quoi comme params ?
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name= "page", defaultValue="0") int page,
            @RequestParam(name= "size", defaultValue = "5") int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistoryPages(accountId, page, size);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, InsufficientBalanceException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, InsufficientBalanceException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, InsufficientBalanceException {
        this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
}
