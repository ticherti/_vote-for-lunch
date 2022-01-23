package com.github.ticherti.voteforlunch.web.menuitem;

import com.github.ticherti.voteforlunch.dto.MenuItemTO;
import com.github.ticherti.voteforlunch.mapper.MenuItemMapper;
import com.github.ticherti.voteforlunch.repository.MenuItemRepository;
import com.github.ticherti.voteforlunch.util.JsonUtil;
import com.github.ticherti.voteforlunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.ticherti.voteforlunch.web.menuitem.MenuItemTestData.*;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.NOT_FOUND;
import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.PARK_CAFE_ID;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.ADMIN_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = ADMIN_MAIL)
class AdminMenuControllerTest extends AbstractControllerTest {

    @Autowired
    private MenuItemRepository repository;
    @Autowired
    private MenuItemMapper mapper;

    public static final String REST_URL = AdminMenuController.REST_URL + '/';

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + PARK_BURGER_ID, PARK_CAFE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(parkBurger));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND_ID, PARK_CAFE_ID))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "/by-date", PARK_CAFE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_ITEM_TO_MATCHER.contentJson(parkBeet, parkBurger));
    }

    @Test
    void createWithLocation() throws Exception {
        MenuItemTO newItem = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL, PARK_CAFE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isCreated());
        MenuItemTO created = (MenuItemTO) MENU_ITEM_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newItem.setId(newId);
        MENU_ITEM_TO_MATCHER.assertMatch(created, newItem);
        MENU_ITEM_TO_MATCHER.assertMatch(mapper.getDTO(repository.getById(newId)), newItem);
    }

    @Test
    void createNotNew() throws Exception {
        MenuItemTO newItem = MenuItemTestData.getNew();
        newItem.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.post(REST_URL, PARK_CAFE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newItem)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void update() throws Exception {
        MenuItemTO updated = getUpdated();
        updated.setId(null);

        perform(MockMvcRequestBuilders.put(REST_URL + PARK_BURGER_ID, PARK_CAFE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MENU_ITEM_TO_MATCHER.assertMatch(mapper.getDTO(repository.getById(PARK_BURGER_ID)), getUpdated());
    }

    @Test
    void updateNotFound() throws Exception {
        MenuItemTO updated = MenuItemTestData.getUpdated();
        updated.setId(NOT_FOUND);

        perform(MockMvcRequestBuilders.put(REST_URL + NOT_FOUND, PARK_CAFE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PARK_BURGER_ID, PARK_CAFE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND, PARK_CAFE_ID))
                .andExpect(status().isUnprocessableEntity());
    }
}