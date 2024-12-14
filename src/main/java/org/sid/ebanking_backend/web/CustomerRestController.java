package org.sid.ebanking_backend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking_backend.dto.CustomerDTO;
import org.sid.ebanking_backend.exceptions.CustomerNotFoundException;
import org.sid.ebanking_backend.repositories.CustomerRepository;
import org.sid.ebanking_backend.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/customers")
@AllArgsConstructor // Injection des dépendences
@Slf4j
public class CustomerRestController {
    // Le controller communique uniquement avec la couche Service
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomerDTOList() {
        return bankAccountService.listCustomers();
    }

    // @TODO - 1 [timecode 1:47:43]
    @GetMapping("/customers/{id}") // time code 1:46
    // il faut indiquer que la valeur de l'id est customerId
    // on affecte "id" à customerId avec PathVariable
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }
}
