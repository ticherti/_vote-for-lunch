package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.UserTO;
import com.github.ticherti.voteforlunch.mapper.UserMapper;
import com.github.ticherti.voteforlunch.model.User;
import com.github.ticherti.voteforlunch.repository.UserRepository;
import com.github.ticherti.voteforlunch.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static com.github.ticherti.voteforlunch.config.WebSecurityConfig.PASSWORD_ENCODER;
import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.assureIdConsistent;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private static final String NOTFOUND = "User not found with id ";

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public User get(int id) {
        log.info("Geting a user by id {}", id);
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(NOTFOUND + id));
    }

    public Optional<User> getByEmail(String email) {
        log.info("Geting a user by email {}", email);
        return userRepository.getByEmail(email);
    }

    public List<User> getAll() {
        log.info("Geting all users");
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @Transactional
    @Modifying
    public void delete(int id) {
        log.info("Deleting a user with id {}", id);
        userRepository.deleteExisted(id);
    }

    @Transactional
    @Modifying
    public User save(User user) {
        log.info("Saving a user");
        Assert.notNull(user, "User must not be null");
        return userRepository.save(prepareToSave(user));
    }

    @Transactional
    @Modifying
    public User save(UserTO userTO) {
        log.info("Saving a user from TO");
        Assert.notNull(userTO, "User must not be null");
        return save(mapper.getNewEntity(userTO));
    }

    @Transactional
    @Modifying
    public void update(UserTO userTO, User user) {
        log.info("Updating a user from TO");
        Assert.notNull(userTO, "User must not be null");
        Assert.notNull(user, "User must not be null");
        userRepository.save(prepareToSave(mapper.updateFromTo(user, userTO)));
    }

    @Transactional
    @Modifying
    public void update(User user, int id) {
        log.info("Updating a user with id {}", id);
        Assert.notNull(user, "User must not be null");
        assureIdConsistent(user, id);
        userRepository.save(prepareToSave(user));
    }

    @Transactional
    @Modifying
    public void enable(int id, boolean enabled) {
        log.info("Enabling {}", enabled);
        User user = userRepository.getById(id);
        user.setEnabled(enabled);
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.debug("Authenticating '{}'", email);
        Optional<User> optionalUser = getByEmail(email);
        return new AuthUser(optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User '" + email + "' was not found")));
    }

    private User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
