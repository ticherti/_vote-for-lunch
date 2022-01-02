package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.exceptions.TooLateToVoteException;
import com.github.ticherti.voteforlunch.mapper.VoteMapper;
import com.github.ticherti.voteforlunch.model.Vote;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import com.github.ticherti.voteforlunch.repository.UserRepository;
import com.github.ticherti.voteforlunch.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VoteService {
//    todo minor insert deadline into properties
    private static final LocalTime DEADLINE_TIME = LocalTime.of(11, 00);

    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteMapper mapper;

    public VoteTO get(int id, LocalDate date) {
//        todo Optional here. Decide checking. Now it's get. Check nulls
        return mapper.getDTO(voteRepository.getByDateAndUserId(id, date));
    }

    public List<VoteTO> getAllByDate(LocalDate date) {
        return mapper.getDTO(voteRepository.getAllByDate(date));
    }

    public List<VoteTO> getAllByRestaurantAndDate(int retaurantId, LocalDate date) {
        return mapper.getDTO(voteRepository.getAllByDateAndRestaurantId(retaurantId, date));
    }

    public VoteTO create(VoteTO voteTO) {
//        todo BIG add auth user id here. WTF
//        todo minor. Format time to string
        final LocalTime currentTime = LocalTime.now();
        int id = voteTO.getUserId();
        Vote vote = mapper.getEntity(voteTO);
        Vote existed = voteRepository.getByDateAndUserId(id, LocalDate.now());
        if (existed!=null) {
            if (currentTime.isAfter(DEADLINE_TIME)){
                throw new TooLateToVoteException(currentTime.toString());
            }
            vote.setId(existed.getId());
        }

//            todo Here. Insert auth user. Clean the userRepo field
        vote.setUser(userRepository.getById(id));
        vote.setRestaurant(restaurantRepository.getById(voteTO.getRestaurantId()));

        return mapper.getDTO(voteRepository.save(vote));
    }
//todo add User id here
//    todo CHECK ID right for user
//    public Vote getByDate(LocalDate date) {
//    return voteRepository.getById(date);
//    }
//todo add User here
//    public List<Vote> getAllByDate(LocalDate date) {
//        return voteRepository.getAllByDateAndUserId(date, 1);
//    }
//
//    public List<Vote> getAllByDate(LocalDate date) {
//        return voteRepository.getAllByDateAndRestaurantId(date);
//    }
}
