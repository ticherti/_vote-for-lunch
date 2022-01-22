package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.MenuItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuItemRepository extends BaseRepository<MenuItem> {

    @Query("SELECT i FROM MenuItem i WHERE i.id=:id AND i.restaurant.id=:restaurantId")
    Optional<MenuItem> findByRestaurant(int id, int restaurantId);

    @Query("SELECT i FROM MenuItem i WHERE i.restaurant.id=:restaurantId AND i.date=:date ORDER BY i.name")
    List<MenuItem> getAllByRestaurantAndDate(int restaurantId, LocalDate date);
}
