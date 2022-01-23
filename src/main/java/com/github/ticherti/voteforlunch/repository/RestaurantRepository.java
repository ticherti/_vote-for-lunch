package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"menuItems"})
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menuItems m WHERE r.id=:id AND m.date=:date ORDER BY m.name")
    Optional<Restaurant> findWithMenu(int id, LocalDate date);

    @EntityGraph(attributePaths = {"menuItems"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN FETCH r.menuItems m WHERE m.date=:date ORDER BY r.name, m.name")
    List<Restaurant> getAllWithMenus(LocalDate date);
}
