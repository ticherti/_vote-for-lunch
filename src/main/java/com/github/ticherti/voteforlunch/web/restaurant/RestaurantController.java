package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.dto.RestaurantWithMenuTO;
import com.github.ticherti.voteforlunch.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {

    static final String REST_URL = "/api/restaurants";
    private final RestaurantService restaurantService;

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable int id) {
        log.info("Getting a restaurant with id {}", id);
        return restaurantService.get(id);
    }

    @GetMapping("/{id}/with-menu")
    public RestaurantWithMenuTO getWithMenu(@PathVariable int id) {
        log.info("Getting a restaurant with id {} with menu", id);
        return restaurantService.getWithMenu(id);
    }

    @GetMapping
    public List<RestaurantTO> getAll() {
        log.info("Getting all restaurants");
        return restaurantService.getAll();
    }

    @GetMapping("/with-menu")
    public List<RestaurantWithMenuTO> getAllWithMenus() {
        log.info("Getting restaurants with menus");
        return restaurantService.getAllWithMenus();
    }
}
