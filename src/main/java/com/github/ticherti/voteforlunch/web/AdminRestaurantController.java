package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.checkNew;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";
    private final RestaurantService restaurantService;

//    todo Add validation like @NotNull and @Valid

    @GetMapping("/{id}")
    public RestaurantTO get(@PathVariable int id) {
        return restaurantService.get(id);
    }

    @GetMapping
    public List<RestaurantTO> getAll() {
        log.info("Getting all the restaurants");
        return restaurantService.getAll();
    }

    //    todo IF Entity move to TO then check out the checkNew() so it still has id
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantTO> createWithLocation(@RequestBody RestaurantTO restaurant) {
        log.info("creating with location");
        checkNew(restaurant);
        RestaurantTO created = restaurantService.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RestaurantTO restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant, id);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        restaurantService.delete(id);
    }
}
