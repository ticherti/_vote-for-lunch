package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.dto.RestaurantWithMenuTO;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.getFound;

@RestController
@Slf4j
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/restaurants";

    public RestaurantController(RestaurantRepository restaurantRepository, RestaurantMapper mapper) {
        super(restaurantRepository, mapper);
    }

    @GetMapping("/{id}/with-menu")
    public RestaurantWithMenuTO getWithMenu(@PathVariable int id) {
        log.info("Getting a restaurant with id {} with menu", id);
        return mapper.getWithMenuDTO(getFound(restaurantRepository.findWithMenu(id, LocalDate.now())));
    }

    @Cacheable("restaurantMenus")
    @GetMapping("/with-menu")
    public List<RestaurantWithMenuTO> getAllWithMenus() {
        log.info("Getting restaurants with menus");
        return mapper.getWithMenuDTO(restaurantRepository.getAllWithMenus(LocalDate.now()));
    }
}
