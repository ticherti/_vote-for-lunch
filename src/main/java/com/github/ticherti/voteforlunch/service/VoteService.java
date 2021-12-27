package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.model.Vote;
import com.github.ticherti.voteforlunch.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoteService {
    private VoteRepository voteRepository;
//todo add User id here
//    public Vote getByDate(LocalDate date) {
//    return voteRepository.getById(date);
//    }
//todo add User here
//    public List<Vote> getAllByDate(LocalDate date) {
//        return voteRepository.getAllByDateAndUserId(date, 1);
//    }
//
//    public List<Vote> getAllByDate(LocalDate date) {
//        return voteRepository.getAllByDateAndRestaurantId(date);
//    }
}
