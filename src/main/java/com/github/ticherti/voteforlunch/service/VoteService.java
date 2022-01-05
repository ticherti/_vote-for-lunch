package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.exception.TooLateToVoteException;
import com.github.ticherti.voteforlunch.mapper.VoteMapper;
import com.github.ticherti.voteforlunch.model.Vote;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import com.github.ticherti.voteforlunch.repository.UserRepository;
import com.github.ticherti.voteforlunch.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
        log.info("Service get id {}, date {}", id, date);
        return mapper.getDTO(voteRepository.getByDateAndUserId(id, date));
    }

    public List<VoteTO> getAllByDate(LocalDate date) {
        log.info("Service get all by date {}", date);
        return mapper.getDTO(voteRepository.getAllByDate(date));
    }

    public List<VoteTO> getAllByRestaurantAndDate(int restaurantId, LocalDate date) {
        log.info("Service get all by restaurant id {}, date {}", restaurantId, date);
        return mapper.getDTO(voteRepository.getAllByDateAndRestaurantId(restaurantId, date));
    }

    @Transactional
    @Modifying
    public VoteTO save(VoteTO voteTO, int userId) {
        Assert.notNull(voteTO, "Vote mustn't be null");
        final LocalTime currentTime = LocalTime.now();
        log.info("Service save. Time is {}", currentTime);

        Vote vote = mapper.getEntity(voteTO);
        Vote existed = voteRepository.getByDateAndUserId(userId, LocalDate.now());
        if (existed != null) {
            if (currentTime.isAfter(DEADLINE_TIME)) {
                throw new TooLateToVoteException(currentTime);
            }
            vote.setId(existed.getId());
        }
        vote.setUser(userRepository.getById(userId));
        vote.setRestaurant(restaurantRepository.getById(voteTO.getRestaurantId()));

        return mapper.getDTO(voteRepository.save(vote));
    }
}
