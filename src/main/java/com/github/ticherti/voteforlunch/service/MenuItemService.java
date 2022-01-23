package com.github.ticherti.voteforlunch.service;

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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.getFound;

@Service
@AllArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "restaurantMenus")
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemMapper mapper;

    public MenuItemTO get(int restaurantId, int id) {
        log.info("Getting an item for the restaurant {} with id {}", restaurantId, id);
        return mapper.getDTO(findByRestaurant(restaurantId, id));
    }

    public List<MenuItemTO> getAll(int restaurantId, @Nullable LocalDate date) {
        log.info("Getting all items");
        return mapper.getDTO(menuItemRepository.getAllByRestaurantAndDate(restaurantId, date));
    }

    @Transactional
    @Modifying
    @CacheEvict(allEntries = true)
    public MenuItemTO save(int restaurantId, MenuItemTO itemTO) {
        log.info("Service creating menu item");
        Assert.notNull(itemTO, "Item must not be null");

        MenuItem item = mapper.getEntity(itemTO);
        item.setRestaurant(restaurantRepository.getById(restaurantId));
        return mapper.getDTO(menuItemRepository.save(item));
    }

    @Transactional
    @Modifying
    @CacheEvict(allEntries = true)
    public void update(MenuItemTO itemTO, int restaurantId) {
        int id = itemTO.getId();
        log.info("Updating menuitem with id {}", id);
        Assert.notNull(itemTO, "Item must not be null");
        findByRestaurant(restaurantId, id);

        MenuItem item = mapper.getEntity(itemTO);
        item.setRestaurant(restaurantRepository.getById(restaurantId));
        menuItemRepository.save(item);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        menuItemRepository.deleteExisted(id);
    }

    private MenuItem findByRestaurant(int restaurantId, int id) {
        return getFound(menuItemRepository.findByRestaurant(id, restaurantId));
    }
}
