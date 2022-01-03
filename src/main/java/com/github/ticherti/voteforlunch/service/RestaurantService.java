package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.exception.NotFoundException;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
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
    private final RestaurantMapper mapper;

    public RestaurantTO get(int id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id));
        return mapper.getLazyDTO(restaurant);
    }

    public RestaurantTO getWithMenu(int id) {
        return mapper.getEagerDTO(restaurantRepository.findWithMenu(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id)));
    }

    public List<RestaurantTO> getAll() {
        log.info("getting all");
        return mapper.getLazyDTO(restaurantRepository.findAll());
    }

    public List<RestaurantTO> getAllWithMenus() {
        return mapper.getEagerDTO(restaurantRepository.getAllWithMenus(LocalDate.now()));
    }

    @Transactional
    @Modifying
    public RestaurantTO create(RestaurantTO restaurantTO) {
        return mapper.getLazyDTO(restaurantRepository.save(mapper.getEntity(restaurantTO)));
    }

    @Transactional
    @Modifying
//    todo AGAIN check what s up with updated id
    public void update(RestaurantTO restaurantTO, int id) {
        Assert.notNull(restaurantTO, "Restaurant must not be null");
        log.info("Updating restaurant with id " + restaurantTO.getId());
        restaurantRepository.save(mapper.getEntity(restaurantTO));
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
