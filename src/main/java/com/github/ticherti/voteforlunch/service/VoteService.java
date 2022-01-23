package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.exception.TooLateToModifyException;
import com.github.ticherti.voteforlunch.mapper.VoteMapper;
import com.github.ticherti.voteforlunch.model.User;
import com.github.ticherti.voteforlunch.model.Vote;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import com.github.ticherti.voteforlunch.repository.VoteRepository;
import com.github.ticherti.voteforlunch.util.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.getFound;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {

    private final DateTimeUtil dateTimeUtil;
    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteMapper mapper;

    public VoteTO get(int id, LocalDate date) {
        log.info("Service get vote id {}, date {}", id, date);
        return mapper.getDTO(getFound(voteRepository.getByDateAndUserId(id, date)));
    }

    @Transactional
    @Modifying
    public VoteTO save(int restaurantId, User user) {
        Assert.notNull(user, "User must not be null");
        log.info("Service save vote");
        final LocalTime currentTime = LocalTime.now();

        Optional<Vote> existed = voteRepository.getByDateAndUserId(user.id(), LocalDate.now());
        Vote vote = existed.isEmpty() ? new Vote() : existed.get();
        if (existed.isPresent() && currentTime.isAfter(dateTimeUtil.getTimeLimit())) {
            throw new TooLateToModifyException(currentTime);
        }
        vote.setUser(user);
        vote.setRestaurant(restaurantRepository.getById(restaurantId));

        return mapper.getDTO(voteRepository.save(vote));
    }
}
