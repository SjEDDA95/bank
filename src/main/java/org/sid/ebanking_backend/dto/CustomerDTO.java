package org.sid.ebanking_backend.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    // On crée une DTO
    private Long id;
    private String customerName;
    private String customerEmail;
}
