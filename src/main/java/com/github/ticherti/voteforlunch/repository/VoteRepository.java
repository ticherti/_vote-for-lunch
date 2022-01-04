package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Vote getByDateAndUserId(int userId, LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.date=:date ORDER BY v.restaurant.name")
    List<Vote> getAllByDate(LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.date=:date")
    List<Vote> getAllByDateAndRestaurantId(int restaurantId, LocalDate date);
}
