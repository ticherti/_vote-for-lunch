package com.github.ticherti.voteforlunch.mapper;

import com.github.ticherti.voteforlunch.dto.UserTO;
import com.github.ticherti.voteforlunch.model.Role;
import com.github.ticherti.voteforlunch.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    default User getNewEntity(UserTO userTO) {
        return new User(null, userTO.getName(), userTO.getEmail(), userTO.getPassword(), Role.USER);
    }

    default User updateFromTo(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setEmail(userTO.getEmail().toLowerCase());
        user.setPassword(userTO.getPassword());
        return user;
    }

}
