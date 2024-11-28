package org.sid.ebanking_backend.entities;

import java.util.List;

public class Customer {
    private Long id;
    private String customerName;
    private String customerEmail;
    // Un client peut avoir plusieurs comptes
    private List<BankAccount> bankAccounts;
}
