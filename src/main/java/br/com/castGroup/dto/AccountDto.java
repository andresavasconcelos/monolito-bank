package br.com.castGroup.dto;

import javax.validation.constraints.NotBlank;

public class AccountDto {

    @NotBlank(message = "Nome do titular é obrigatório")
    private String ownerName;

    @NotBlank(message = "Número da conta é obrigatório")
    private String accountNumber;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
