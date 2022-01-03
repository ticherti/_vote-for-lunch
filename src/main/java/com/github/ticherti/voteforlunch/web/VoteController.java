package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    //todo Must be authentication check by user. Votes go with auth user id
//    TODO BIG User should be returned with or withoud votes. How a user can get his today vote?
    static final String REST_URL = "/api/votes";
    private final VoteService voteService;

    //    todo Get 1 should be renamed and only for today and auth user. Change mapping
    @GetMapping("/user/{id}")
    public VoteTO getByUserToday(@PathVariable int id) {
        return voteService.get(id, LocalDate.now());
    }

    @GetMapping("/all")
    public List<VoteTO> getAllByDate() {
        return voteService.getAllByDate(LocalDate.now());
    }

    @GetMapping("/{restaurantId}")
    public List<VoteTO> getAllByRestaurantAndDate(@PathVariable int restaurantId) {
        return voteService.getAllByRestaurantAndDate(restaurantId, LocalDate.now());
    }

    @PostMapping
    public ResponseEntity<VoteTO> save(@RequestBody VoteTO voteTO) {
//        todo this one should be changed to be send by Auth user

        log.info("creating with location");
        checkNew(voteTO);
        VoteTO created = voteService.create(voteTO);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
