package br.com.castGroup.repository;

import br.com.castGroup.model.Account;
import br.com.castGroup.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Busca todas as transações em que a conta aparece como origem ou destino, ordenadas por timestamp desc
    @Query("SELECT t FROM Transaction t " +
            "WHERE t.sourceAccount = :acc OR t.destinationAccount = :acc " +
            "ORDER BY t.timestamp DESC")
    List<Transaction> findByAccount(@Param("acc") Account account);
}
