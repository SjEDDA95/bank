package org.sid.ebanking_backend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking_backend.dto.*;
import org.sid.ebanking_backend.entities.*;
import org.sid.ebanking_backend.enums.AccountStatus;
import org.sid.ebanking_backend.enums.OperationType;
import org.sid.ebanking_backend.exceptions.BankAccountNotFoundException;
import org.sid.ebanking_backend.exceptions.CustomerNotFoundException;
import org.sid.ebanking_backend.exceptions.InsufficientBalanceException;
import org.sid.ebanking_backend.mappers.BankAccountMapperImpl;
import org.sid.ebanking_backend.repositories.AccountOperationRepository;
import org.sid.ebanking_backend.repositories.BankAccountRepository;
import org.sid.ebanking_backend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    private BankAccountMapperImpl dtoMapper;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving a customer => <{}>", customerDTO.toString());
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    // @TODO - 4 étape 8 [timecode 2:43:10]
    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, Long customerID, double overDraft) throws CustomerNotFoundException {
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

        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }

    // @TODO - 4 étape 9 [timecode 2:43:10]
    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, Long customerID, double interestRate) throws CustomerNotFoundException {
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

        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer ->
                dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
    }

    // @TODO - 4 étape 11
    // Polymorphisme, ajout de l'instanceOf
    @Override
    public BankAccountDTO getBankAccount(String accountID) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountID)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found !!!"));
        // Un petit test pour savoir quel type de compte (current - saving)
        if(bankAccount instanceof SavingAccount) {
            SavingAccount savingAccount = (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount = (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }

    @Override
    public void debit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        // @TODO - 4 étape 12
        BankAccount bankAccount = getAccount(accountID);

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

    private BankAccount getAccount(String accountID) throws BankAccountNotFoundException {
        BankAccount bankAccount = bankAccountRepository.findById(accountID)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found !!!"));
        return bankAccount;
    }

    @Override
    public void credit(String accountID, double amount, String description) throws BankAccountNotFoundException, InsufficientBalanceException {
        // @TODO - 4 étape 13
        BankAccount bankAccount = bankAccountRepository.findById(accountID)
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found !!!"));
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


    }

    @Override
    public List<BankAccountDTO> bankAccountList() {
        // @TODO - 4 étape 14, transfèrer une liste
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountDTOs = bankAccounts.stream().map(bankAcc -> /* ici on fait le test (curr,save) */{
            if (bankAcc instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) bankAcc;
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAcc;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountDTOs;
    }

    // @TODO - 1 [timecode 1:47:43]
    // Méthode pour retourner un utilisateur
    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found by his id"));
        return dtoMapper.fromCustomer(customer);
    }

    // @TODO - 3 [timecode 2:02:55] supprimer un client
    @Override
    public void deleteCustomer(Long customerId) throws CustomerNotFoundException {
        if(!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("CUSTOMER NOT FOUND BY ID");
        }
        customerRepository.deleteById(customerId);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating a customer => <{}>", customerDTO.toString());
        Customer customer = dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }

    // @TODO 6 étape 4
    @Override
    public List<AccountOperationDTO> accountHistoryOperations(String accountId) {
        List<AccountOperation> accountOperationList = accountOperationRepository.findByBankAccountId(accountId);
        List<AccountOperationDTO> accountOperationListDTO = accountOperationList.stream().map(operation ->
                dtoMapper.fromAccountOperation(operation)).collect(Collectors.toList());
        return accountOperationListDTO;
    }

    // @TODO 6 étape 12
    @Override
    public AccountHistoryDTO getAccountHistoryPages(String accountId, int page, int size) throws BankAccountNotFoundException {
        // @TODO 6 étape 14 A REVOIR VRAIMENT
        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElse(null);

        if(bankAccount == null) throw new BankAccountNotFoundException("ACCOUNT NO FOUND");

        Page<AccountOperation> accountOperations = accountOperationRepository.findPageByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();

        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(operation -> dtoMapper.fromAccountOperation(operation)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }


}
