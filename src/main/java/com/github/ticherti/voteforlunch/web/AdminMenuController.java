package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.model.MenuItem;
import com.github.ticherti.voteforlunch.service.MenuItemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(value = "api/admin/restaurants/{restaurantId}/menuitems", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AdminMenuController {
//    todo Mapping names probably should be changed for the test on CONSTANTS
//    look out for create method rest url
//    private final String PART_OF_REST_URL = "api/admin/restaurants/{restaurantId}/menuitems";

    //    todo add service here instead of Repo
    private final MenuItemService menuItemService;

    //todo Change entities for the TOs
    @GetMapping("/{id}")
    public MenuItem get(@PathVariable int restaurantId, @PathVariable int id) {
//        Сйлейманов использует Файнд
        return menuItemService.get(restaurantId, id);
    }

    @GetMapping("")
    public List<MenuItem> getAllByDate(@PathVariable int restaurantId) {
//        todo Add Sorting for lists
        return menuItemService.getAll(restaurantId, LocalDate.now());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MenuItem> createWithLocation(@PathVariable int restaurantId, @RequestBody MenuItem item) {
        final String REST_URL = String.format("api/admin/restaurants/%s/menuitems", restaurantId);
        MenuItem created = menuItemService.create(restaurantId, item);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @RequestBody MenuItem item) {
        menuItemService.update(restaurantId, item);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int restaurantId, @PathVariable int id) {
        menuItemService.delete(restaurantId, id);
    }


}
