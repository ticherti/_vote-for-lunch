package com.github.ticherti.voteforlunch.web.restaurant;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.*;

@RestController
@Slf4j
@CacheConfig(cacheNames = {"restaurants", "restaurantMenus"})
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    public AdminRestaurantController(RestaurantRepository restaurantRepository, RestaurantMapper mapper) {
        super(restaurantRepository, mapper);
    }

    @CacheEvict(allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTO> createWithLocation(@Valid @RequestBody RestaurantTO restaurantTO) {
        log.info("creating with location");
        checkNew(restaurantTO);

        RestaurantTO created = mapper.getDTO(restaurantRepository.save(mapper.getEntity(restaurantTO)));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @Modifying
    @CacheEvict(allEntries = true)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTO restaurant, @PathVariable int id) {
        log.info("Updating a restaurant with id {}", id);
        assureIdConsistent(restaurant, id);
        getFound(restaurantRepository.findById(id));
        restaurantRepository.save(mapper.getEntity(restaurant));
    }

    @CacheEvict(allEntries = true)
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Deleting a restaurant with id {}", id);
        restaurantRepository.deleteExisted(id);
    }
}
