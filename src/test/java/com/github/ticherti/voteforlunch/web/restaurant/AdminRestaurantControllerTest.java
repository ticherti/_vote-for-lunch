package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import com.github.ticherti.voteforlunch.util.JsonUtil;
import com.github.ticherti.voteforlunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.ticherti.voteforlunch.web.restaurant.RestaurantTestData.*;
import static com.github.ticherti.voteforlunch.web.user.UserTestData.ADMIN_MAIL;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithUserDetails(value = ADMIN_MAIL)
class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository repository;
    @Autowired
    private RestaurantMapper mapper;

    public static final String REST_URL = AdminRestaurantController.REST_URL + '/';

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
                .andExpect(status().isNotFound());
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
    void createWithLocation() throws Exception {
        RestaurantTO newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());
        RestaurantTO created = (RestaurantTO) RESTAURANT_TO_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_TO_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_TO_MATCHER.assertMatch(mapper.getDTO(repository.getById(newId)), newRestaurant);
    }

    @Test
    void createNotNew() throws Exception {
        RestaurantTO newRestaurant = RestaurantTestData.getNew();
        newRestaurant.setId(NOT_FOUND);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void update() throws Exception {
        RestaurantTO updated = getUpdated();
        updated.setId(null);

        perform(MockMvcRequestBuilders.put(REST_URL + PARK_CAFE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_TO_MATCHER.assertMatch(mapper.getDTO(repository.getById(PARK_CAFE_ID)), getUpdated());
    }

    @Test
    void updateNotFound() throws Exception {
        RestaurantTO updated = RestaurantTestData.getUpdated();
        updated.setId(NOT_FOUND);

        perform(MockMvcRequestBuilders.put(REST_URL + NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + PARK_CAFE_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + NOT_FOUND))
                .andExpect(status().isUnprocessableEntity());
    }
}