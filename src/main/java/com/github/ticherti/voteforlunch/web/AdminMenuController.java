package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.model.MenuItem;
import com.github.ticherti.voteforlunch.service.MenuItemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.ValidationUtil.assureIdConsistent;
import static com.github.ticherti.voteforlunch.util.ValidationUtil.checkNew;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(value = AdminMenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminMenuController {
    final static String REST_URL = "api/admin/restaurants/{restaurantId}/menuitems";

    private final MenuItemService menuItemService;

    //todo Change entities for the TOs
    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int restaurantId, @PathVariable int id) {
        return menuItemService.get(restaurantId, id);
    }

    @GetMapping
    public List<MenuItem> getAllByDate(@PathVariable int restaurantId) {
//        todo Add Sorting for lists
        log.info("Getting the today menu for the restaurant");
        return menuItemService.getAll(restaurantId, LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@PathVariable int restaurantId, @RequestBody MenuItem item) {
        log.info("Creating menu item");
        checkNew(item);
        MenuItem created = menuItemService.create(restaurantId, item);
        log.info(created.getId().toString());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
//    todo No consistent check for id of restaurants here. Have in mind that there might be some in service like get()
    public void update(@PathVariable int restaurantId, @RequestBody MenuItem item, @PathVariable int id) {
        assureIdConsistent(item, id);
        menuItemService.update(restaurantId, item, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        menuItemService.delete(restaurantId, id);
    }
}

