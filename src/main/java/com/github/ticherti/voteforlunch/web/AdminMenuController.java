package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.dto.MenuItemTO;
import com.github.ticherti.voteforlunch.service.MenuItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.checkNew;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {
    final static String REST_URL = "api/admin/restaurants/{restaurantId}/menuitems";

    private final MenuItemService menuItemService;

    @GetMapping("/{id}")
    public MenuItemTO get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("Getting an item for the restaurant {} with id", restaurantId, id);
        return menuItemService.get(restaurantId, id);
    }

    @GetMapping
    public List<MenuItemTO> getAllByDate(@PathVariable int restaurantId,
                                         @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting the today menu for the restaurant {}", restaurantId);
        return menuItemService.getAll(restaurantId, date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItemTO> createWithLocation(@PathVariable int restaurantId, @Valid @RequestBody MenuItemTO item) {
        log.info("Creating menu item");
        checkNew(item);
        MenuItemTO created = menuItemService.save(restaurantId, item);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @Valid @RequestBody MenuItemTO item, @PathVariable int id) {
        log.info("Updating menuitem with id {}", id);
        menuItemService.update(restaurantId, item, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("Deleting menuitem with id {}", id);
        menuItemService.delete(restaurantId, id);
    }
}

