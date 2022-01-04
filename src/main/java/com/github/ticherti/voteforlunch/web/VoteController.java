package com.github.ticherti.voteforlunch.web;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/votes";
    static final String USER_URL = "/user";
    private final VoteService voteService;

    @GetMapping(USER_URL)
    public VoteTO getByUserAndDate(@AuthenticationPrincipal AuthUser authUser,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting a user's vote");
        return voteService.get(authUser.id(), getDate(date));
    }

    @GetMapping("/today")
    public List<VoteTO> getAllByDate(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting all votes");
        return voteService.getAllByDate(getDate(date));
    }

    @GetMapping("/{restaurantId}")
    public List<VoteTO> getAllByRestaurantAndDate(
            @NotNull @PathVariable int restaurantId,
            @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting all votes by restaurant id {}", restaurantId);
        return voteService.getAllByRestaurantAndDate(restaurantId, date);
    }

    @PostMapping
    public ResponseEntity<VoteTO> createWithLocation(@Valid @RequestBody VoteTO voteTO, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Creating with location");
        VoteTO created = voteService.save(voteTO, authUser.id());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + USER_URL)
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    private LocalDate getDate(LocalDate date) {
        return (date == null) ? LocalDate.now() : date;
    }
}
