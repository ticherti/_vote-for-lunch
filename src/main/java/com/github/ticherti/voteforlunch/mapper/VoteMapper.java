package com.github.ticherti.voteforlunch.mapper;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "restaurant.id", target = "restaurantId")
    VoteTO getDTO(Vote vote);

    List<VoteTO> getDTO(List<Vote> votes);
}


