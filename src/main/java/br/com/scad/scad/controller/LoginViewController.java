package br.com.scad.scad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //utilizado para pagina web
public class LoginViewController {
    @GetMapping("/login")
    public String paginalogin(){
        return "login";
    }
}
