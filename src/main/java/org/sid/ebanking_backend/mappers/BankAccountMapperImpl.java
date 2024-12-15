package org.sid.ebanking_backend.mappers;

import org.sid.ebanking_backend.dto.CurrentBankAccountDTO;
import org.sid.ebanking_backend.dto.CustomerDTO;
import org.sid.ebanking_backend.dto.SavingBankAccountDTO;
import org.sid.ebanking_backend.entities.CurrentAccount;
import org.sid.ebanking_backend.entities.Customer;
import org.sid.ebanking_backend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {

    /**
     * Converts a Customer entity into a CustomerDTO.
     * @param customer The Customer entity to convert.
     * @return The corresponding CustomerDTO.
     */
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    /**
     * Converts a CustomerDTO into a Customer entity.
     * @param customerDTO The CustomerDTO to convert.
     * @return The corresponding Customer entity.
     */
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    // @TODO - 4 étape 4
    /**
     * Converts a SavingAccount entity into a SavingBankAccountDTO.
     * Maps the Customer entity to CustomerDTO.
     * @param savingAccount The SavingAccount entity to convert.
     * @return The corresponding SavingBankAccountDTO.
     */
    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount) {
        SavingBankAccountDTO savingBankAccountDTO = new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        // Il va pas transfèrer le client (objet de type CustomerDTO) il transfère les attributs
        // Donc on lui dit, tu me transfères les attributs, mais une fois qu'il les a transfèrer
        // On doit compléter
        savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer())); // Obtenue suite a la copie de propriétés
        return savingBankAccountDTO;
    }

    // @TODO - 4 étape 5
    /**
     * Converts a SavingBankAccountDTO into a SavingAccount entity.
     * Maps the CustomerDTO to Customer entity.
     * @param savingBankAccountDTO The SavingBankAccountDTO to convert.
     * @return The corresponding SavingAccount entity.
     */
    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO) {
        SavingAccount savingAccount = new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO, savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;
    }

    // @TODO - 4 étape 6
    /**
     * Converts a CurrentAccount entity into a CurrentBankAccountDTO.
     * Maps the Customer entity to CustomerDTO.
     * @param currentAccount The CurrentAccount entity to convert.
     * @return The corresponding CurrentBankAccountDTO.
     */
    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount) {
        CurrentBankAccountDTO currentBankAccountDTO = new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentAccount.getCustomer()));
        return currentBankAccountDTO;
    }

    // @TODO - 4 étape 7
    /**
     * Converts a CurrentBankAccountDTO into a CurrentAccount entity.
     * Maps the CustomerDTO to Customer entity.
     * @param currentBankAccountDTO The CurrentBankAccountDTO to convert.
     * @return The corresponding CurrentAccount entity.
     */
    public CurrentAccount fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO) {
        CurrentAccount currentAccount = new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO, currentAccount);
        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentAccount;
    }
}
