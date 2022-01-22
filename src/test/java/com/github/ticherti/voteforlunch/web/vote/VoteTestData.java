package com.github.ticherti.voteforlunch.web.vote;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.web.MatcherFactory;

import java.time.LocalDate;

import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.JOE_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.PARK_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.ADMIN_ID;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.USER_ID;

public class VoteTestData {
    static MatcherFactory.Matcher<VoteTO> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTO.class);


    public static final int USER_VOTE_ID = 3;

    static VoteTO userVote = new VoteTO(USER_VOTE_ID, LocalDate.now(), USER_ID, PARK_CAFE_ID);

    static VoteTO getNew() {
        return new VoteTO(4, LocalDate.now(), ADMIN_ID, PARK_CAFE_ID);
    }

    static VoteTO getUpdated() {
        return new VoteTO(userVote.id(), LocalDate.now(), USER_ID, JOE_CAFE_ID);
    }
}
