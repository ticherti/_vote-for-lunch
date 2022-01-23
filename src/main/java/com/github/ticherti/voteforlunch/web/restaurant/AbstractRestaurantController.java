package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.getFound;

@RestController
@Slf4j
@CacheConfig(cacheNames = {"restaurants", "restaurantMenus"})
public abstract class AbstractRestaurantController {
    final RestaurantRepository restaurantRepository;
    final RestaurantMapper mapper;

    public AbstractRestaurantController(RestaurantRepository restaurantRepository, RestaurantMapper mapper) {
        this.restaurantRepository = restaurantRepository;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable int id) {
        log.info("Get restaurant with id {}", id);
        return mapper.getDTO(getFound(restaurantRepository.findById(id)));
    }

    @Cacheable("restaurants")
    @GetMapping
    public List<RestaurantTO> getAll() {
        log.info("Getting all the restaurants");
        return mapper.getDTO(restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }
}