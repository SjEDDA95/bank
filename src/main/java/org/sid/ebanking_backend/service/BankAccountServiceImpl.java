package org.sid.ebanking_backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking_backend.entities.*;
import org.sid.ebanking_backend.enums.AccountStatus;
import org.sid.ebanking_backend.enums.OperationType;
import org.sid.ebanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.ebanking_backend.exceptions.CustomerNotFoundException;
import org.sid.ebanking_backend.exceptions.InsufficientBalanceException;
import org.sid.ebanking_backend.repositories.AccountOperationRepository;
import org.sid.ebanking_backend.repositories.BankAccountRepository;
import org.sid.ebanking_backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
// Loguer les messages
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{
    // Injection des dependence avec constructeur avec paramètres (AllArgsConstructor)

    private CustomerRepository customerRepository;

    private BankAccountRepository bankAccountRepository;

    private AccountOperationRepository accountOperationRepository;

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving a customer");
        Customer savedCustomer = customerRepository.save(customer);
        return savedCustomer;
    }

    @Override
    public CurrentAccount saveCurrentBankAccount(double initialBalance, Long customerID, double overDraft) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setAccountStatus(AccountStatus.CREATED);

        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);

        return savedBankAccount;
    }

    @Override
    public SavingAccount saveSavingBankAccount(double initialBalance, Long customerID, double interestRate) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerID).orElse(null);
        if(customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setAccountStatus(AccountStatus.CREATED);

        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);

        return savedBankAccount;
    }


    @Override
    public List<Customer> listCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountID) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountID)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found !!!"));
        return bankAccount;
    }

    @Override
    public void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        BankAccount bankAccount = getBankAccount(accountID);
        if(bankAccount.getBalance() < amount) {
            throw new InsufficientBalanceException("Balance of account insufficient");
        }
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        BankAccount bankAccount = getBankAccount(accountID);
        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, InsufficientBalanceException {

        debit(accountIdSource, amount, "DEBIT OPERATION");
        log.info("##### Processing debit from account : <{}> of amount : <{}>", accountIdSource, amount);
        credit(accountIdDestination, amount, "CREDIT OPERATION");
        log.info("##### Processing credit to account destination : <{}> of amount : <{}>", accountIdDestination, amount);

        

        BankAccount bankAccountSender = getBankAccount(accountIdSource);
        BankAccount bankAccountReceiver = getBankAccount(accountIdDestination);



    }
}
