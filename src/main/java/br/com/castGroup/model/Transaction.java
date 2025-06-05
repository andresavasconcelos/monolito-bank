package br.com.castGroup.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Conta de origem (pode ser nula em caso de crédito externo)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;

    // Conta de destino (pode ser nula em caso de débito para fora)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;

    // Valor da operação
    @Column(nullable = false)
    private BigDecimal amount;

    // Tipo: “CREDIT”, “DEBIT” ou “TRANSFER”
    @Column(nullable = false, length = 10)
    private String type;

    // Data e hora da transação
    @Column(nullable = false)
    private LocalDateTime timestamp;

    public Transaction() { }

    public Transaction(Account sourceAccount,
                       Account destinationAccount,
                       BigDecimal amount,
                       String type,
                       LocalDateTime timestamp) {
        this.sourceAccount      = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount             = amount;
        this.type               = type;
        this.timestamp          = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
