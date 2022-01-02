package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    //    todo Check oup where should be @Transactional
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Vote getByDateAndUserId(int userId, LocalDate date);

    //    todo check queries
//todo transactional!
    @Query("SELECT v FROM Vote v WHERE v.date=:date")
    List<Vote> getAllByDate(LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.date=:date")
    List<Vote> getAllByDateAndRestaurantId(int restaurantId, LocalDate date);
}
