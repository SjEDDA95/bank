package org.sid.ebanking_backend.dto;

import lombok.Data;

// @TODO ETAPE TEST
@Data
public class CreditDTO {
    private String accountId;
    private double amount;
    private String description;
}
