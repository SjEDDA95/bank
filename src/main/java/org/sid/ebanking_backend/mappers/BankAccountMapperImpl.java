package org.sid.ebanking_backend.mappers;

import org.sid.ebanking_backend.dto.CustomerDTO;
import org.sid.ebanking_backend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }

    /**
     * Takes a CustomerDTO object in input and outputs a Customer object
     * @param customerDTO customerDTO
     * @return Customer
     */
    public Customer fromCustomerDTO(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
