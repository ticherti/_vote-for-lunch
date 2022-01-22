package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.ticherti.voteforlunch.web.menuitem.MenuItemTestData.*;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.*;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.USER_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = USER_MAIL)
class RestaurantControllerTest extends AbstractControllerTest {
    public static final String REST_URL = RestaurantController.REST_URL + '/';

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PARK_CAFE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(parkCafe));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getWithMenu() throws Exception {
        parkCafeWithMenu.setMenuItems(List.of(parkBeet, parkBurger));
        perform(MockMvcRequestBuilders.get(REST_URL + PARK_CAFE_MENU_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(parkCafeWithMenu));
    }

    @Test
    void geNotFoundtWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_MENU_URL))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(parkCafe, joeCafe));
    }

    @Test
    void getWithMenus() throws Exception {
        parkCafeWithMenu.setMenuItems(List.of(parkBurger, parkBeet));
        joeCafeWithMenu.setMenuItems(List.of(joeBurger, joeCola));
        perform(MockMvcRequestBuilders.get(REST_URL + "/menus"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(parkCafeWithMenu, joeCafeWithMenu));
    }
}