package br.com.castGroup.util;

import br.com.castGroup.model.Account;
import br.com.castGroup.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public DataLoader(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountRepository.count() == 0) {
            Account a1 = new Account("Alice", "ACC1001");
            a1.setBalance(new BigDecimal("1000.00"));
            Account a2 = new Account("Bob", "ACC1002");
            a2.setBalance(new BigDecimal("500.00"));
            accountRepository.save(a1);
            accountRepository.save(a2);
        }
    }
}
