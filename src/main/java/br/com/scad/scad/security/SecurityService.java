package br.com.scad.scad.security;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityService {
    private  final UserService userService;

    public SecurityService(UserService userService) {
        this.userService = userService;
    }
    //todo essa classe retorna o usuario logado atraves do Authentication uma interface que contem o
    // contexto da aplicação e consegue recuperar atraves do UserDetails uma instancia do usuario
    // logado com todas as suas infomações

    public UserDomain getUserAuth(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication){
            return customAuthentication.getUserRequest();
        }
        return  null;
    }
}
