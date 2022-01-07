package com.github.ticherti.voteforlunch.web.menuitem;

import com.github.ticherti.voteforlunch.dto.MenuItemTO;
import com.github.ticherti.voteforlunch.web.MatcherFactory;

import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.JOE_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.PARK_CAFE_ID;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher MENU_ITEM_TO_MATCHER = MatcherFactory.usingEqualsComparator(MenuItemTO.class);

    public static final int PARK_BURGER_ID = 1;
    public static final int PARK_BEET_ID = 2;
    public static final int JOE_BURGER_ID = 3;
    public static final int JOE_COLD_ID = 4;
    public static final int NOT_FOUND_ID = 0;

    public static final MenuItemTO parkBurger = new MenuItemTO(PARK_BURGER_ID, "Burger with turkey", 400, PARK_CAFE_ID);
    public static final MenuItemTO parkBeet = new MenuItemTO(PARK_BEET_ID, "Beets with sesame", 200, PARK_CAFE_ID);
    public static final MenuItemTO joeBurger = new MenuItemTO(JOE_BURGER_ID, "Bigburger", 300, JOE_CAFE_ID);
    public static final MenuItemTO joeCola = new MenuItemTO(JOE_COLD_ID, "Cola", 50, JOE_CAFE_ID);

    public static MenuItemTO getNew() {
        return new MenuItemTO(null, "New food", 0, PARK_CAFE_ID);
    }

    public static final MenuItemTO getUpdated() {
        return new MenuItemTO(PARK_BURGER_ID, "Updated food", 999, PARK_CAFE_ID);
    }
}
