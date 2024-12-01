package org.sid.ebanking_backend;

import org.sid.ebanking_backend.entities.*;
import org.sid.ebanking_backend.enums.AccountStatus;
import org.sid.ebanking_backend.enums.OperationType;
import org.sid.ebanking_backend.repositories.AccountOperationRepository;
import org.sid.ebanking_backend.repositories.BankAccountRepository;
import org.sid.ebanking_backend.repositories.CustomerRepository;
import org.sid.ebanking_backend.service.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BankService bankService) {
		return args -> {
			bankService.consult();
		};
	}

	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository accountOperationRepository) {
		return args -> {
			// liste de clients
			Stream.of("Albert", "Gilbert", "Robert").forEach(name -> {
				Customer customer = new Customer();
				// Je définis ses infos
				customer.setCustomerName(name);
				customer.setCustomerEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(cust -> {
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 1000);
				currentAccount.setCreatedAt(new Date()); // date système
				currentAccount.setAccountStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 1000);
				savingAccount.setCreatedAt(new Date()); // date système
				savingAccount.setAccountStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(4.4);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(account -> {
				for (int i = 0; i < 10; i++) {
					// pour chaque compte je veux créer 5 opérations
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random() * 234);
					accountOperation.setOperationType(Math.random() > 0.5 ?  OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(account);
					accountOperationRepository.save(accountOperation);
				}

			});
		};
	}
}
