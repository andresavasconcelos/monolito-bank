package br.com.castGroup.controller;

import br.com.castGroup.dto.TransactionDto;
import br.com.castGroup.model.Account;
import br.com.castGroup.model.Transaction;
import br.com.castGroup.service.AccountServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final AccountServiceImpl accountService;

    public UserController(AccountServiceImpl accountService) {
        this.accountService = accountService;
    }


    @GetMapping("/transactions")
    public String showTransactionForm(Model model) {
        model.addAttribute("transactionDto", new TransactionDto());
        return "user/account-transactions";
    }

    @PostMapping("/credit")
    public String credit(@ModelAttribute("transactionDto") @Valid TransactionDto dto,
                         BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/account-transactions";
        }
        try {
            accountService.credit(dto.getAccountNumber(), dto.getAmount());
            model.addAttribute("message", "Crédito realizado com sucesso");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "user/account-transactions";
    }

    @PostMapping("/debit")
    public String debit(@ModelAttribute("transactionDto") @Valid TransactionDto dto,
                        BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/account-transactions";
        }
        try {
            accountService.debit(dto.getAccountNumber(), dto.getAmount());
            model.addAttribute("message", "Débito realizado com sucesso");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "user/account-transactions";
    }

    @GetMapping("/transfer")
    public String showTransferForm(Model model) {
        model.addAttribute("transactionDto", new TransactionDto());
        return "user/transfer";
    }

    @PostMapping("/transfer")
    public String transfer(@ModelAttribute("transactionDto") @Valid TransactionDto dto,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/transfer";
        }
        try {
            accountService.transfer(dto.getAccountNumber(),
                    dto.getDestinationAccountNumber(),
                    dto.getAmount());
            model.addAttribute("message", "Transferência realizada com sucesso");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "user/transfer";
    }

    @GetMapping("/balance")
    public String showBalanceForm(Model model) {
        model.addAttribute("accountNumber", "");
        return "user/balance";
    }

    @PostMapping("/balance")
    public String getBalance(@RequestParam("accountNumber") String accountNumber,
                             Model model) {
        try {
            Account acc = accountService.getAccount(accountNumber);
            model.addAttribute("balance", acc.getBalance());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("accountNumber", accountNumber);
        return "user/balance";
    }

    @GetMapping("/statement")
    public String showStatementForm(Model model) {
        model.addAttribute("accountNumber", "");
        return "user/statement";
    }

    @PostMapping("/statement")
    public String getStatement(@RequestParam("accountNumber") String accountNumber,
                               Model model) {
        try {
            List<Transaction> transactions = accountService.getTransactionsByAccount(accountNumber);
            model.addAttribute("transactions", transactions);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("accountNumber", accountNumber);
        return "user/statement";
    }
}