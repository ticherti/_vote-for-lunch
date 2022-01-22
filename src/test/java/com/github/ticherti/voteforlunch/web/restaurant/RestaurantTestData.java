package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.dto.RestaurantWithMenuTO;
import com.github.ticherti.voteforlunch.web.MatcherFactory;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTO.class, "restaurantId");

    public static final int PARK_CAFE_ID = 1;
    public static final int JOE_CAFE_ID = 2;
    public static final int NOT_FOUND = 0;
    public static final String PARK_CAFE_MENU_URL = PARK_CAFE_ID + "/menus";
    public static final String NOT_FOUND_MENU_URL = NOT_FOUND + "/menus";

    public static final RestaurantTO parkCafe = new RestaurantTO(1, "Central Park Café");
    public static final RestaurantTO joeCafe = new RestaurantTO(2, "Deleting Joe");
    public static final RestaurantWithMenuTO parkCafeWithMenu = new RestaurantWithMenuTO(1, "Central Park Café", null);
    public static final RestaurantWithMenuTO joeCafeWithMenu = new RestaurantWithMenuTO(2, "Deleting Joe", null);

    public static RestaurantTO getNew() {
        return new RestaurantTO(null, "New");
    }

    public static RestaurantTO getUpdated() {
        return new RestaurantTO(1, "Updated Cafe");
    }
}
