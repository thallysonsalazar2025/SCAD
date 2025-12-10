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
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

@Component
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String PASSWORD_STANDARD = "123";
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
        String nameUser = oAuth2User.getAttribute("name");

        //todo busco o usuario com o email autenticadono google
        UserDomain user = userService.findUserByEmail(email);
        if(user == null){
            user = createNewUserDomain(email, nameUser);
        }


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

    private UserDomain createNewUserDomain(String email, String nameUser) {
        UserRegistrationRequest userCad = new UserRegistrationRequest();
        userCad.setEmail(email);
        userCad.setLogin(userService.getLogionByMail(email));
        userCad.setDateInclusion(LocalDate.now());
        userCad.setDateUpdated(LocalDate.now());
        userCad.setPassword(PASSWORD_STANDARD);
        userCad.setName(nameUser);
        //todo futuramente integrar uma api para consultar o usario pelo nome data de nascimento e retornar o cpf do cliente
        userCad.setCpf(userService.getCpfByApiClientGov(nameUser));
        userCad.setRoles(Collections.singletonList("USER"));
        return userService.createNewUserIn(userCad);
    }
}
