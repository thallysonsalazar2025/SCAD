package br.com.scad.scad.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller //utilizado para pagina web
public class LoginViewController {
    @GetMapping("/login")
    public String paginalogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String paginaHome(Authentication authentication){
        return "home" + authentication.getName();
    }
}
