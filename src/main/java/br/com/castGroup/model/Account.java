package br.com.castGroup.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    @Min(value = 0, message = "Saldo não pode ser negativo")
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Versão para controle de concorrência otimista.
     */
    @Version
    private Integer version;

    public Account(Long id, String ownerName, String accountNumber, BigDecimal balance, Integer version) {
        this.id = id;
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.version = version;
    }

    public Account() { }

    public Account(String ownerName, String accountNumber) {
        this.ownerName = ownerName;
        this.accountNumber = accountNumber;
        this.balance = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}