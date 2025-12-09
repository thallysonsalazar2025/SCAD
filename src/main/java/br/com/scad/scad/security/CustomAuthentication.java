package br.com.scad.scad.security;

import br.com.scad.scad.domain.UserDomain;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class CustomAuthentication implements Authentication {
    private  final UserDomain userRequest;

    public CustomAuthentication(UserDomain userRequest) {
        this.userRequest = userRequest;
    }
    //todo atráves desse método retornaremos uma lista de authority, no qual é recuperado do usuario logado todas
    // as roles e incluida na lista de authority's que é retornado
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return userRequest.getRoles()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect((Collectors.toList()));

    }

    public UserDomain getUserRequest() {
        return userRequest;
    }

    @Override
    public Object getCredentials() {
        return userRequest;
    }

    @Override
    public Object getDetails() {
        return userRequest;
    }

    @Override
    public Object getPrincipal() {
        return userRequest;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return userRequest.getLogin();
    }
}
