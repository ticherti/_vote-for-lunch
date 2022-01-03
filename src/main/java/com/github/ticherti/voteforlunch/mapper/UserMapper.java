package com.github.ticherti.voteforlunch.mapper;

import com.github.ticherti.voteforlunch.dto.UserTO;
import com.github.ticherti.voteforlunch.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

//        return new User(null, userTo.getName(), userTo.getEmail().toLowerCase(), userTo.getPassword(), Role.USER);


//        @Mappings({@Mapping(source = "email.toLowerCase", target = "email")})
        User getEntity (UserTO userTO);

        UserTO getDTO (User user);

        List<UserTO> getDTO (List < User > users);

    }
