package com.github.ticherti.voteforlunch.web.vote;

import com.github.ticherti.voteforlunch.dto.VoteTO;
import com.github.ticherti.voteforlunch.service.VoteService;
import com.github.ticherti.voteforlunch.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api/votes";
    static final String USER_URL = "/mine";
    private final VoteService voteService;

    @GetMapping(USER_URL)
    public VoteTO getByUserAndDate(@AuthenticationPrincipal AuthUser authUser,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Getting a user's vote");
        return voteService.get(authUser.id(), getDate(date));
    }

    @PostMapping
    public ResponseEntity<VoteTO> createWithLocation(@NotNull @RequestParam int restaurantId,
                                                     @AuthenticationPrincipal AuthUser authUser) {
        log.info("Creating with location");
        VoteTO created = voteService.save(restaurantId, authUser.getUser());
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + USER_URL)
                .queryParam("date", LocalDate.now())
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@NotNull @RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Updating vote");
        voteService.save(restaurantId, authUser.getUser());
    }

    private LocalDate getDate(LocalDate date) {
        return (date == null) ? LocalDate.now() : date;
    }
}
