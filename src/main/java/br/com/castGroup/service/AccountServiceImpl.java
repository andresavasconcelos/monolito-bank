package br.com.castGroup.service;

import br.com.castGroup.exception.InsufficientFundsException;
import br.com.castGroup.exception.ResourceNotFoundException;
import br.com.castGroup.model.Account;
import br.com.castGroup.model.Transaction;
import br.com.castGroup.repository.AccountRepository;
import br.com.castGroup.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountServiceImpl(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Account createAccount(String ownerName, String accountNumber) {
        Account acc = new Account(ownerName, accountNumber);
        return accountRepository.save(acc);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Account credit(String accountNumber, BigDecimal amount) {
        Account acc = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + accountNumber));
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de crédito deve ser positivo");
        }
        acc.setBalance(acc.getBalance().add(amount));
        Account updated = accountRepository.save(acc);

        // Registrar transação de crédito (sourceAccount = null, destinationAccount = acc)
        Transaction tx = new Transaction(
                null,
                updated,
                amount,
                "CREDIT",
                LocalDateTime.now()
        );
        transactionRepository.save(tx);

        return updated;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Account debit(String accountNumber, BigDecimal amount) {
        Account acc = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + accountNumber));
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de débito deve ser positivo");
        }
        if (acc.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para débito");
        }
        acc.setBalance(acc.getBalance().subtract(amount));
        Account updated = accountRepository.save(acc);

        // Registrar transação de débito (sourceAccount = acc, destinationAccount = null)
        Transaction tx = new Transaction(
                updated,
                null,
                amount,
                "DEBIT",
                LocalDateTime.now()
        );
        transactionRepository.save(tx);

        return updated;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Contas de origem e destino devem ser diferentes");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de transferência deve ser positivo");
        }

        // BLOQUEIO PESSIMISTA
        Account fromAcc = accountRepository.findByAccountNumberForUpdate(fromAccountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Conta de origem não encontrada"));
        Account toAcc   = accountRepository.findByAccountNumberForUpdate(toAccountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Conta de destino não encontrada"));

        if (fromAcc.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Saldo insuficiente para transferência");
        }

        fromAcc.setBalance(fromAcc.getBalance().subtract(amount));
        toAcc.setBalance(toAcc.getBalance().add(amount));

        accountRepository.save(fromAcc);
        accountRepository.save(toAcc);

        // Registrar transação de transferência (sourceAccount = fromAcc, destinationAccount = toAcc)
        Transaction tx = new Transaction(
                fromAcc,
                toAcc,
                amount,
                "TRANSFER",
                LocalDateTime.now()
        );
        transactionRepository.save(tx);
    }

    @Transactional(readOnly = true)
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + accountNumber));
    }

    @Transactional(readOnly = true)
    public List<Account> listAllAccounts() {
        return accountRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        Account acc = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada: " + accountNumber));
        return transactionRepository.findByAccount(acc);
    }
}