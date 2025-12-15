package br.com.scad.scad.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String login() {
        // Retorna a view 'login' -> src/main/resources/templates/login.html (Thymeleaf)
        return "login";
    }
    @GetMapping("/")
    @ResponseBody
    public String homePage(Authentication authentication) {
        return "<h1>Bem-vindo, " + authentication.getName() + "!</h1><p>Você está autenticado.</p>";
    }

    @GetMapping("/authorized")
    @ResponseBody
    public String googleCallback(@RequestParam("code") String code) {
        return "<h1>Callback do Google</h1><p>Seu código de autorização é:</p><pre>" + code + "</pre>";
    }
}
