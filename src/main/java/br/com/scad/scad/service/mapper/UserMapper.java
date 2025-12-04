package br.com.scad.scad.service.mapper;
import br.com.scad.scad.domain.UserDomain;
import br.com.scad.scad.generated.model.UserRegistrationRequest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(user.getPassword()))")
    UserDomain toUser(UserRegistrationRequest user, PasswordEncoder passwordEncoder);
}
