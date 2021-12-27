package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    //    todo Check oup where should be @Transactional
//    todo Check how this method works
    Vote getByDateAndUserId(LocalDate date, int id);
//todo transactional!
    List<Vote> getAllByDateAndUserId(LocalDate date, int id);

    List<Vote> getAllByDateAndRestaurantId(LocalDate date, int id);
}
