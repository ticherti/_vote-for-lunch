package com.github.ticherti.voteforlunch.web.user;

import com.github.ticherti.voteforlunch.dto.UserTO;
import com.github.ticherti.voteforlunch.model.User;
import com.github.ticherti.voteforlunch.service.UserService;
import com.github.ticherti.voteforlunch.web.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.assureIdConsistent;
import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.checkNew;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "users")
public class ProfileController extends AbstractUserController {

    static final String REST_URL = "/api/profile";
    private final UserService userService;

    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Get profile");
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", allEntries = true)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("Deleting by auth user");
        userService.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(allEntries = true)
    public ResponseEntity<User> register(@Valid @RequestBody UserTO userTo) {
        log.info("register {}", userTo);
        checkNew(userTo);
        User created = userService.save(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(allEntries = true)
    public void update(@RequestBody @Valid UserTO userTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("Update {}", userTo);
        assureIdConsistent(userTo, authUser.id());
        User user = authUser.getUser();
        userService.update(userTo, user);
    }

}
