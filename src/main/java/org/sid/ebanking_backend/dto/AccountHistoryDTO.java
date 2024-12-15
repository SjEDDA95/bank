package org.sid.ebanking_backend.dto;


import lombok.Data;

import java.util.List;

// @TODO 6 étape 8, on veut afficher des informations supplémentaires dans l'historique
@Data
public class AccountHistoryDTO {
    private String accountId;

    // @TODO 6 étape 9, Pagination
    private int currentPage;
    private int totalPages;
    private int pageSize;

    private List<AccountOperationDTO> accountOperationDTOS;
    private double balance;
}
