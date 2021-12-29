package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

//    todo check out how many queries
//todo check out sorting
    @Query("SELECT i FROM MenuItem i WHERE i.restaurant.id=:restaurantId AND i.date=:date")
    List<MenuItem> getAllByRestaurantAndDate(int restaurantId, LocalDate date);
}
