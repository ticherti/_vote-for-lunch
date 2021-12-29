package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.exceptions.NotBelongException;
import com.github.ticherti.voteforlunch.exceptions.NotFoundException;
import com.github.ticherti.voteforlunch.model.MenuItem;
import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.repository.MenuItemRepository;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MenuItemService {
    //    todo Add consistency checks
    private static final String NOTFOUND = "Menu item not found with id ";
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItem get(int restaurantId, int id) {
        MenuItem item = menuItemRepository.findById(id).orElseThrow(() -> new NotFoundException(NOTFOUND + id));
        checkBelonging(item, restaurantId);
        return item;
    }

    //todo find out where should it be sorted
    public List<MenuItem> getAll(int restaurantId, LocalDate date) {
        log.info("Getting all items");
        return menuItemRepository.getAllByRestaurantAndDate(restaurantId, date);
    }

    @Transactional
    @Modifying
    public MenuItem create(int restaurantId, MenuItem item) {
        log.info("Service creating menu item");
        item.setRestaurant(checkPresentRestaurant(restaurantId));
        item.setDate(LocalDate.now());
        return menuItemRepository.save(item);
    }

    //todo add here date option. Date for All comes from controller. Should be the same.
    @Transactional
    @Modifying
//    todo Not sure if i need int id here in parameters
    public void update(int restaurantId, MenuItem item, int id) {
        checkBelonging(item, restaurantId);
        item.setRestaurant(checkPresentRestaurant(restaurantId));
//      todo This leads to resetting any old dish to current date. Probably should add checks for item date to be actual. Or not.
        item.setDate(LocalDate.now());
        menuItemRepository.save(item);
    }

    @Transactional
    @Modifying
    public void delete(int restaurantId, int id) {
        get(restaurantId, id);
        menuItemRepository.deleteById(id);
    }

    //todo Probably should move it to repos layer. Kind of double responsibility (Restaurant)
    @Transactional(propagation = Propagation.MANDATORY)
    Restaurant checkPresentRestaurant(int restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new NotFoundException("The restaurant for this menu item is not found with id " + restaurantId));
    }

    private void checkBelonging(MenuItem item, int restaurantId) {
        int id = item.getRestaurant().getId();
        if (id != restaurantId) {
            throw new NotBelongException(String.format("Menu item with id %s doesn't belong to the restaurant with id %s",
                    id, restaurantId));
        }
    }
}
