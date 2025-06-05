package br.com.castGroup.controller;

import br.com.castGroup.dto.AccountDto;
import br.com.castGroup.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AccountServiceImpl accountService;

    /**
     * Exibe lista de todas as contas.
     */
    @GetMapping("/accounts")
    public String listAccounts(Model model) {
        model.addAttribute("accounts", accountService.listAllAccounts());
        return "admin/list-accounts";
    }

    /**
     * Formulário para criar nova conta.
     */
    @GetMapping("/accounts/new")
    public String showCreateForm(Model model) {
        model.addAttribute("accountDto", new AccountDto());
        return "admin/create-account";
    }

    /**
     * Salva a nova conta.
     */
    @PostMapping("/accounts")
    public String createAccount(@ModelAttribute("accountDto") @Valid AccountDto dto,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/create-account";
        }
        accountService.createAccount(dto.getOwnerName(), dto.getAccountNumber());
        return "redirect:/admin/accounts";
    }
}
