package com.github.ticherti.voteforlunch.web.vote;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.mapper.VoteMapper;
import com.github.ticherti.voteforlunch.repository.VoteRepository;
import com.github.ticherti.voteforlunch.util.TimeUtil;
import com.github.ticherti.voteforlunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalTime;

import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.JOE_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.PARK_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.ADMIN_MAIL;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.USER_MAIL;
import static com.github.ticherti.voteforlunch.web.vote.VoteController.REST_URL;
import static com.github.ticherti.voteforlunch.web.vote.VoteController.USER_URL;
import static com.github.ticherti.voteforlunch.web.vote.VoteTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoteMapper mapper;
    @Autowired
    private TimeUtil timeUtil;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(userVote));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        VoteTO newVote = VoteTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(PARK_CAFE_ID)))
                .andExpect(status().isCreated());
        VoteTO created = VOTE_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_TO_MATCHER.assertMatch(created, newVote);
        VOTE_TO_MATCHER.assertMatch(mapper.getDTO(voteRepository.getById(newId)), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        VoteTO updated = VoteTestData.getUpdated();
        updated.setId(null);
        timeUtil.setTimeLimit(LocalTime.now().plusSeconds(1));

        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(JOE_CAFE_ID)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_TO_MATCHER.assertMatch(mapper.getDTO(voteRepository.getById(USER_VOTE_ID)), VoteTestData.getUpdated());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfter() throws Exception {
        timeUtil.setTimeLimit(LocalTime.now().minusSeconds(1));

        perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(JOE_CAFE_ID)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}