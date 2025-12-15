package br.com.scad.scad.security;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtCustomAuthenticationFilter extends OncePerRequestFilter {
    private final UserService userService;

    public JwtCustomAuthenticationFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (goToConvert(authentication)) {
            String userAuth = authentication.getName();
            UserDomain userDomain = userService.findUserByLogin(userAuth);
            if (userDomain == null) {
                authentication = new CustomAuthentication(userDomain);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }

    private Boolean goToConvert(Authentication authentication) {
        return authentication != null && authentication instanceof JwtAuthenticationToken;
    }
}
