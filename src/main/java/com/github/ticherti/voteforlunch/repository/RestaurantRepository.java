package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    //todo Add @Transactional(readOnly = true)
//    todo Add Modifying
//    todo Find out what's with r.id=?1 - 1???
    //    EntityGraph.EntityGraphType.LOAD is for not doubling lines created by sql join
//    todo so delete type if restaurant doesn't have more than one Eager list
    @EntityGraph(attributePaths = {"menu_items"})
    @Query("SELECT r FROM Restaurant r JOIN r.menuItems m WHERE r.id=:id AND m.date=:date")
    Restaurant getWithMenus(int id, LocalDate date);

    @EntityGraph(attributePaths = {"menu_items"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r JOIN r.menuItems m WHERE m.date=:date")
    List<Restaurant> getAllWithMenus(LocalDate date);
}
