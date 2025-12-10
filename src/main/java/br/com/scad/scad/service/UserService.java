package br.com.scad.scad.service;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.domain.validator.CPFGenerator;
import br.com.scad.scad.generated.model.UserRegistrationRequest;
import br.com.scad.scad.repository.UserRepository;
import br.com.scad.scad.service.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final CPFGenerator cpfGenerator;


    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, CPFGenerator cpfGenerator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.cpfGenerator = cpfGenerator;
    }

    public UserDomain createNewUserIn(UserRegistrationRequest userRequest) {
        UserDomain user = userMapper.toUser(userRequest, passwordEncoder);
        return userRepository.save(user);
    }

    public UserDomain findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }
    public UserDomain findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String getLogionByMail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    public String getCpfByApiClientGov(String user) {
        System.out.println(user);
        return CPFGenerator.gerarCPF();
    }
}
