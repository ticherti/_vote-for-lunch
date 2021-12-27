package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.model.Vote;
import com.github.ticherti.voteforlunch.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/votes";
    private final VoteService voteService;

//    @GetMapping("/{id}")
//    public Vote get(@PathVariable int id){
//        return voteService.get(id);
//    }
//
//    @GetMapping("")
//    public List<Vote> getAll(){
//        return voteService.getAllByDate(LocalDate.now());
//    }
}
