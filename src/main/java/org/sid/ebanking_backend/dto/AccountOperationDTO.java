package org.sid.ebanking_backend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebanking_backend.entities.BankAccount;
import org.sid.ebanking_backend.enums.OperationType;

import java.util.Date;

// @TODO 6 étape 2, création du DTO et cleaning

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType operationType;
    private String description;
}
