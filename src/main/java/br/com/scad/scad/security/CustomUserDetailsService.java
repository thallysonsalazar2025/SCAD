package br.com.scad.scad.security;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.service.UserService;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }


    // todo cria um UserDetails personalizado onde eu estou passando os valores que recebo na requisição
    //  utilizando a classe UserDomain
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDomain userAplication = userService.findUserByLogin(login);

        return User.builder()
                .username(userAplication.getLogin())
                .password(userAplication.getPassword())
                .roles(userAplication.getRoles().toArray(new String[0]))
                .build();
    }

}
