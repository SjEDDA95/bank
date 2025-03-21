package org.sid.ebanking_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebanking_backend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    // si on veut spécifier le bon énumérateur au lieu du chiffre dans h2
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @ManyToOne
    private Customer customer;
    // différence lazy eager
    // lazy : charge-moi tout sans les opérations
    // eager : charge tout
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.EAGER)
    // Eager pas la meilleure solution, il faudra gérer dans le service
    private List<AccountOperation> accountOperations;
}
