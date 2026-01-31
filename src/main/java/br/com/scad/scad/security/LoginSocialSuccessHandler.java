package br.com.scad.scad.security;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.generated.model.UserRegistrationRequest;
import br.com.scad.scad.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

@Component
public class LoginSocialSuccessHandler implements AuthenticationSuccessHandler {
    public static final String PASSWORD_STANDARD = "123";
    private final UserService userService;

    public LoginSocialSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();

        String email = oAuth2User.getAttribute("email");
        String nameUser = oAuth2User.getAttribute("name");

        UserDomain user = userService.findUserByEmail(email);
        if(user == null){
            user = createNewUserDomain(email, nameUser);
        }

        authentication  = new CustomAuthentication(user);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // CORREÇÃO: Redirecionamento manual relativo para evitar localhost
        response.sendRedirect("/cadastro-usuario");
    }

    private UserDomain createNewUserDomain(String email, String nameUser) {
        UserRegistrationRequest userCad = new UserRegistrationRequest();
        userCad.setEmail(email);
        userCad.setLogin(userService.getLogionByMail(email));
        userCad.setDateInclusion(LocalDate.now());
        userCad.setDateUpdated(LocalDate.now());
        userCad.setPassword(PASSWORD_STANDARD);
        userCad.setName(nameUser);
        userCad.setCpf(userService.getCpfByApiClientGov(nameUser));
        userCad.setRoles(Collections.singletonList("USER"));
        return userService.createNewUserIn(userCad);
    }
}
