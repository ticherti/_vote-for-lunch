package com.github.ticherti.voteforlunch.web.user;

import com.github.ticherti.voteforlunch.dto.UserTO;
import com.github.ticherti.voteforlunch.mapper.UserMapper;
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
// TODO: cache only most requested data!
@CacheConfig(cacheNames = "users")
public class ProfileController extends AbstractUserController {
    //    todo There are transactional methods. Check them, move @Transactional to service if possible

    static final String REST_URL = "/api/profile";
    private final UserService userService;
    private final UserMapper mapper;

        @GetMapping
        public User get(@AuthenticationPrincipal AuthUser authUser) {
            return authUser.getUser();
        }

        @DeleteMapping
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void delete(@AuthenticationPrincipal AuthUser authUser) {
            super.delete(authUser.id());
        }

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @ResponseStatus(HttpStatus.CREATED)
        @CacheEvict(allEntries = true)
        public ResponseEntity<User> register(@Valid @RequestBody UserTO userTo) {
            log.info("register {}", userTo);
            checkNew(userTo);
//            todo I would like this to be simple and be mapped only in service
            User created = prepareAndSave(userService.createNewFromTo(userTo));
            URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(REST_URL).build().toUri();
            return ResponseEntity.created(uriOfNewResource).body(created);
        }


        @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        @ResponseStatus(HttpStatus.NO_CONTENT)
//        This should be moved to service with auth user or be uncommented
//        @Transactional
        @CacheEvict(allEntries = true)
        public void update(@RequestBody @Valid UserTO userTo, @AuthenticationPrincipal AuthUser authUser) {
            assureIdConsistent(userTo, authUser.id());
//            TODO BIG if I can use authUser not here, but in service, I would like to move it there
            User user = authUser.getUser();
            prepareAndSave(userService.updateFromTo(user, userTo));
        }

}
