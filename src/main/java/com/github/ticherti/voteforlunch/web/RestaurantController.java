package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    //    TODO CHANGE THAT FUNCTIONALITY. CHANGING SHOULD BE IN ADMIN FIELD
    static final String REST_URL = "/api/restaurants";
    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return restaurantService.get(id);
    }

    @GetMapping("/{id}/menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        return restaurantService.getWithMenu(id);
    }

    @GetMapping("")
    public List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/menus")
    public List<Restaurant> getAllWithMenus() {
        return restaurantService.getAllWithMenus();
    }
}
