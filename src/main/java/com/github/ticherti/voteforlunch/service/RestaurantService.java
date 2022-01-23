package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.dto.RestaurantWithMenuTO;
import com.github.ticherti.voteforlunch.exception.IllegalRequestDataException;
import com.github.ticherti.voteforlunch.mapper.RestaurantMapper;
import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.getFound;

@Service
@AllArgsConstructor
@Slf4j
@CacheConfig(cacheNames = {"restaurants", "restaurantMenus"})
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper mapper;

    public RestaurantTO get(int id) {
        log.info("Getting a restaurant with id {}", id);
        return mapper.getDTO(getFound(restaurantRepository.findById(id)));
    }

    public RestaurantWithMenuTO getWithMenu(int id) {
        log.info("Getting a restaurant with id {} with menu", id);
        return mapper.getWithMenuDTO(getFound(restaurantRepository.findWithMenu(id, LocalDate.now())));
    }

    @Cacheable("restaurants")
    public List<RestaurantTO> getAll() {
        log.info("Getting all");
        return mapper.getDTO(restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

    @Cacheable("restaurantMenus")
    public List<RestaurantWithMenuTO> getAllWithMenus() {
        log.info("Getting all with menu");
        return mapper.getWithMenuDTO(restaurantRepository.getAllWithMenus(LocalDate.now()));
    }


    @CacheEvict(allEntries = true)
    public RestaurantTO save(RestaurantTO restaurantTO) {
        log.info("Saving restaurant");
        Assert.notNull(restaurantTO, "Restaurant must not be null");
        return mapper.getDTO(restaurantRepository.save(mapper.getEntity(restaurantTO)));
    }

    @Transactional
    @Modifying
    @CacheEvict(allEntries = true)
    public void update(RestaurantTO restaurantTO) {
        int id = restaurantTO.getId();
        log.info("Updating restaurant with id {}", restaurantTO.getId());
        Assert.notNull(restaurantTO, "Restaurant must not be null");
        getFound(restaurantRepository.findById(id));
        restaurantRepository.save(mapper.getEntity(restaurantTO));
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        log.info("Deleting restaurant with id {}", id);
        restaurantRepository.deleteExisted(id);
    }
}
