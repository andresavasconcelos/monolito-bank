package br.com.castGroup.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homeAfterLogin(Authentication auth) {
        // Se tiver ROLE_ADMIN, redireciona para /admin/accounts
        boolean isAdmin = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(r -> r.equals("ROLE_ADMIN"));
        if (isAdmin) {
            return "redirect:/admin/accounts";
        }
        // Caso contrário (usuário comum), redireciona para /user/transactions
        return "redirect:/user/transactions";
    }
}