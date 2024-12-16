package org.sid.ebanking_backend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sid.ebanking_backend.dto.CustomerDTO;
import org.sid.ebanking_backend.exceptions.CustomerNotFoundException;
import org.sid.ebanking_backend.repositories.CustomerRepository;
import org.sid.ebanking_backend.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/customers")
@AllArgsConstructor // Injection des dépendences
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    // Le controller communique uniquement avec la couche Service
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomerDTOList() {
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return bankAccountService.searchCustomers("%"+keyword+"%");
    }

    // @TODO - 1 [timecode 1:47:43]
    @GetMapping("/customers/{id}") // time code 1:46
    // il faut indiquer que la valeur de l'id est customerId
    // on affecte "id" à customerId avec PathVariable
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    // @TODO - 2 [timecode 1:53:50] pour tester POST, il faut utiliser postman
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return bankAccountService.saveCustomer(customerDTO);
    }

    // @TODO - 3 [timecode 2:02:55] supprimer un client/update
    @DeleteMapping("/customers/{customerId}")
    public void deleteCustomer(@PathVariable Long customerId) throws CustomerNotFoundException {
        bankAccountService.deleteCustomer(customerId);
    }

    // @TODO - 3 [timecode 2:02:55] supprimer un client/update
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO) {
        customerDTO.setId(customerId); // car il provient du path et non pas des données
        return bankAccountService.updateCustomer(customerDTO);
    }

    // @NOTA BENE ==> @RequestBody signifie que lorsque on envoie la requete dans le body (sous format
    // JSON (comme le customerName, customerEmail etc..) @RequestBody dit a spring :
    // Regarde le body de la requete HTTP et prend les données la bas et convertit les en objet java

    /*
    Par exemple
    JSON=>
    {
    "customerName" : "Gilbert",
    "customerEmail" : "Gilbert@gmail.com",
    }
    JAVA=>
    CustomerDTO customerDTO = new CustomerDTO();
    customerDTO.setName("Gilbert");
    customerDTO.setEmail("Gilbert@gmail.com")
    saveCUSTOMER=>
    la méthod a un objet CustomerDTO
     */
}
