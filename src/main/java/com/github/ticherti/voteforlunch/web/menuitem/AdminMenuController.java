package com.github.ticherti.voteforlunch.web.menuitem;

import com.github.ticherti.voteforlunch.dto.MenuItemTO;
import com.github.ticherti.voteforlunch.mapper.MenuItemMapper;
import com.github.ticherti.voteforlunch.model.MenuItem;
import com.github.ticherti.voteforlunch.repository.MenuItemRepository;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.*;

@RestController
@AllArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "restaurantMenus")
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {

    static final String REST_URL = "/api/admin/restaurants/{restaurantId}/menu-items";
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemMapper mapper;

    @GetMapping("/{id}")
    public MenuItemTO get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("Getting an item for the restaurant {} with id {}", restaurantId, id);
        return mapper.getDTO(findByRestaurant(restaurantId, id));
    }

    @GetMapping("/by-date")
    public List<MenuItemTO> getAllByDate(@PathVariable int restaurantId,
                                         @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting the menu for the restaurant {}", restaurantId);
        date = (date == null) ? LocalDate.now() : date;
        return mapper.getDTO(menuItemRepository.getAllByRestaurantAndDate(restaurantId, date));
    }

    @Transactional
    @Modifying
    @CacheEvict(allEntries = true)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemTO> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody MenuItemTO itemTO) {
        log.info("Creating menu item");
        checkNew(itemTO);

        MenuItem item = mapper.getEntity(itemTO);
        item.setRestaurant(restaurantRepository.getById(restaurantId));
        MenuItemTO created = mapper.getDTO(menuItemRepository.save(item));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Transactional
    @Modifying
    @CacheEvict(allEntries = true)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody MenuItemTO itemTO, @PathVariable int restaurantId, @PathVariable int id) {
        log.info("Updating menuitem with id {}", id);
        assureIdConsistent(itemTO, id);
        findByRestaurant(restaurantId, id);

        MenuItem item = mapper.getEntity(itemTO);
        item.setRestaurant(restaurantRepository.getById(restaurantId));
        menuItemRepository.save(item);
    }

    @CacheEvict(allEntries = true)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Deleting menuitem with id {}", id);
        menuItemRepository.deleteExisted(id);
    }

    private MenuItem findByRestaurant(int restaurantId, int id) {
        return getFound(menuItemRepository.findByRestaurant(id, restaurantId));
    }
}

