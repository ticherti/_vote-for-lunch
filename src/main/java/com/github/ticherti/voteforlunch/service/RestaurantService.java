package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.exception.NotFoundException;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.assureIdConsistent;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class RestaurantService {
    private static final String NOTFOUND = "Restaurant not found with id ";
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public RestaurantTO get(int id) {
        log.info("Getting a restaurant with id {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id));
        return mapper.getLazyDTO(restaurant);
    }

    public RestaurantTO getWithMenu(int id) {
        log.info("Getting a restaurant with id {} with menu", id);
        return mapper.getEagerDTO(restaurantRepository.findWithMenu(id, LocalDate.now())
                .orElseThrow(() -> new NotFoundException(NOTFOUND + id)));
    }

    public List<RestaurantTO> getAll() {
        log.info("Getting all");
        return mapper.getLazyDTO(restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

    public List<RestaurantTO> getAllWithMenus() {
        log.info("Getting all with menu");
        return mapper.getEagerDTO(restaurantRepository.getAllWithMenus(LocalDate.now()));
    }

    @Transactional
    @Modifying
    public RestaurantTO save(RestaurantTO restaurantTO) {
        log.info("Saving restaurant with id {}", restaurantTO.getId());
        Assert.notNull(restaurantTO, "Restaurant must not be null");
        return mapper.getLazyDTO(restaurantRepository.save(mapper.getEntity(restaurantTO)));
    }

    @Transactional
    @Modifying
    public void update(RestaurantTO restaurantTO, int id) {
        log.info("Updating restaurant with id {}", restaurantTO.getId());
        Assert.notNull(restaurantTO, "Restaurant must not be null");
        assureIdConsistent(restaurantTO, id);
        restaurantRepository.save(mapper.getEntity(restaurantTO));
    }

    @Transactional
    @Modifying
    public void delete(int id) {
        log.info("Deleting restaurant with id {}", id);
        restaurantRepository.deleteExisted(id);
    }
}
