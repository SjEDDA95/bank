package org.sid.ebanking_backend.service;

import org.sid.ebanking_backend.dto.*;
import org.sid.ebanking_backend.entities.*;
import org.sid.ebanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.ebanking_backend.exceptions.CustomerNotFoundException;
import org.sid.ebanking_backend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface BankAccountService {
    // bonnes pratiques : opérations qu'on effectue => couche service
    // pas besoin mots clés dans les interfaces

    // Créer utilisateur
//    Customer saveCustomer(Customer customer);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    // Créer des comptes bancaires
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerID, double overDraft) throws CustomerNotFoundException;

    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerID, double interestRate) throws CustomerNotFoundException;

    // Consulter les comptes des clients
    List<CustomerDTO> listCustomers();

    // Consulter un compte spécifique
    BankAccountDTO getBankAccount(String accountID) throws BankAccountNotFoundException;

    // Créer des opérations de débit, crédit, virement
    void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;

    void credit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, InsufficientBalanceException;

    List<BankAccountDTO> bankAccountList();

    // @TODO - 1 [timecode 1:47:43]
    // Méthode pour retourner un utilisateur
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    // @TODO - 3 [timecode 2:02:55] supprimer un client
    void deleteCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    // @TODO 6 étape 5
    List<AccountOperationDTO> accountHistoryOperations(String accountId);

    // @TODO 6 étape 11
    AccountHistoryDTO getAccountHistoryPages(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
