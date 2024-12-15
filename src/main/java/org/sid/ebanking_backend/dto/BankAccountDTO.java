package org.sid.ebanking_backend.dto;

import lombok.Data;

// @TODO - 4 étape 10 [timecode 2:45:10]
// @TODO - 4 étape 10bis extends BankAccountDTO sur SavingDTO et CurrentDTO
@Data
public class BankAccountDTO {

    // @TODO - 5 étape 1, On ajoute le type de compte
    private String type;
}
