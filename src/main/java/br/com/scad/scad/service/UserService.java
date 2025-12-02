package br.com.scad.scad.service;

import br.com.scad.scad.generated.model.UserRegistrationRequest;
import br.com.scad.scad.domain.User;
import br.com.scad.scad.repository.UserRepository;
import br.com.scad.scad.service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public User createNewUserIn(UserRegistrationRequest user) {
        return userRepository.save(userMapper.toUser(user));
    }
}
