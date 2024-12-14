package org.sid.ebanking_backend.service;

import org.sid.ebanking_backend.dto.CustomerDTO;
import org.sid.ebanking_backend.entities.*;
import org.sid.ebanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.ebanking_backend.exceptions.CustomerNotFoundException;
import org.sid.ebanking_backend.exceptions.InsufficientBalanceException;

import java.util.List;

public interface BankAccountService {
    // bonnes pratiques : opérations qu'on effectue => couche service
    // pas besoin mots clés dans les interfaces

    // Créer utilisateur
    Customer saveCustomer(Customer customer);

    // Créer des comptes bancaires
    CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerID, double overDraft) throws CustomerNotFoundException;

    SavingAccount saveSavingBankAccount(double initialBalance, Long customerID, double interestRate) throws CustomerNotFoundException;

    // Consulter les comptes des clients
    List<CustomerDTO> listCustomers();

    // Consulter un compte spécifique
    BankAccount getBankAccount(String accountID) throws BankAccountNotFoundException;

    // Créer des opérations de débit, crédit, virement
    void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;

    void credit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException;

    void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, InsufficientBalanceException;

    List<BankAccount> bankAccountList();


    // @TODO - 1 [timecode 1:47:43]
    // Méthode pour retourner un utilisateur
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
}
