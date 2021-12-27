package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
//    todo check out how it will catch by restaurant id
//    todo check out how many queries
    List<MenuItem> getAllByRestaurantAndDate(int restaurantId, LocalDate date);
}
