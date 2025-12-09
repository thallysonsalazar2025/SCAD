package br.com.scad.scad.service;

import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.dto.response.UserRegistrationRespose;
import br.com.scad.scad.generated.model.UserRegistrationRequest;
import br.com.scad.scad.repository.UserRepository;
import br.com.scad.scad.service.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
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
}
