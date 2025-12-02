package br.com.scad.scad.service.mapper;
import br.com.scad.scad.generated.model.UserRegistrationRequest;

import br.com.scad.scad.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = User.class)
public interface UserMapper {
    UserRegistrationRequest toUserRegistrationRequest(User user);
    User toUser(UserRegistrationRequest userRegistrationRequest);
}
