package org.sid.ebanking_backend.dto;

import lombok.Data;
import org.sid.ebanking_backend.enums.AccountStatus;

import java.util.Date;

@Data // getters et setters uniquement
// @TODO - 4 étape 3
public class CurrentBankAccountDTO extends BankAccountDTO{
    // DTO représentant un compte courant
    private String id;

    private double balance;

    private Date createdAt;

    private AccountStatus accountStatus;

    private CustomerDTO customerDTO;

    private double overDraft;
}
