package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.exceptions.NotFoundException;
import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RestaurantService {
    //todo change everywhere for @slf4j
    private static final String NOTFOUND = "Restaurant not found with id ";
    private final RestaurantRepository restaurantRepository;

    public Restaurant get(int id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id));
    }

    public Restaurant getWithMenu(int id) {
        return restaurantRepository.findWithMenu(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id));
    }

    public List<Restaurant> getAll() {
        log.info("getting all");
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getAllWithMenus() {
        return restaurantRepository.getAllWithMenus(LocalDate.now());
    }

    @Transactional
    @Modifying
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    @Modifying
    public void update(Restaurant restaurant, int id) {
        Assert.notNull(id, "mealTo must not be null");
        restaurantRepository.save(restaurant);
    }

    //   todo Check out what's up with deleteById method in meal or users. Why add a query method to a repo
//    todo check tj2 final code for deletion

    @Transactional
    @Modifying
    public void delete(int id) {
        restaurantRepository.findById(id).orElseThrow(() -> new NotFoundException(NOTFOUND + id));
        restaurantRepository.deleteById(id);
    }
}
