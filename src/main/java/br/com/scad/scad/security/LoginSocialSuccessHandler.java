package br.com.scad.scad.security;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.dto.UserRegistrationRequest;
import br.com.scad.scad.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final UserService userService;

    public LoginSocialSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        System.out.println(authentication);
        //todo classe responsável por recuperar o usuario autenticado no google
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = auth2AuthenticationToken.getPrincipal();
        //todo recupero o atributo email através do authentication
        String email = oAuth2User.getAttribute("email");

        //todo busco o usuario com o email autenticadono google
        UserDomain user = userService.findUserByEmail(email);


        //todo passo para o meu CustomAuthentication o usuario autenticado no google e modifico
        // a minha authentication para ser uma nova CustomAuthentication
        authentication  = new CustomAuthentication(user);

        // todo recupero o contexto security do spring e injeto a minha authentication
        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        // todo e por fim eu passo para frente chamando outra pagina chamando o método onAuthenticationSuccess
        super.onAuthenticationSuccess(request, response, authentication);

    }
}
