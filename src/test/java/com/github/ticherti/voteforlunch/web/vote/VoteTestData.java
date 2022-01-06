package com.github.ticherti.voteforlunch.web.vote;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.web.MatcherFactory;

import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.FIRST_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.SECOND_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.ADMIN_ID;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.USER_ID;

public class VoteTestData {
    static MatcherFactory.Matcher<VoteTO> VOTE_TO_MATCHER = MatcherFactory.usingEqualsComparator(VoteTO.class);


    public static final int USER_VOTE_ID = 3;
    public static final int ADMIN_VOTE_ID = 0;

    static VoteTO userVote = new VoteTO(USER_VOTE_ID, USER_ID, FIRST_CAFE_ID);

    static VoteTO getNew() {
        return new VoteTO(4, ADMIN_ID, FIRST_CAFE_ID);
    }

    static VoteTO getUpdated() {
        return new VoteTO(userVote.id(), USER_ID, SECOND_CAFE_ID);
    }
}
