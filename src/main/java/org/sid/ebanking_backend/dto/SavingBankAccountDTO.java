package org.sid.ebanking_backend.dto;

import lombok.Data;
import org.sid.ebanking_backend.enums.AccountStatus;

import java.util.Date;

@Data // getters et setters uniquement
// @TODO - 4 étape 2
public class SavingBankAccountDTO extends BankAccountDTO{
    // DTO représentant un compte épargne
    private String id;

    private double balance;

    private Date createdAt;

    private AccountStatus accountStatus;

    // On supprime ManyToOne mais on met CustomerDTO
    // Supposons qu'on a pas besoin de tous les attributs de Customer dans l'objet
    // Nom et id par exemple...
    // Solution ==>
    private CustomerDTO customerDTO;

    private double interestRate;
}
