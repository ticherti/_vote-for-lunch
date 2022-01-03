package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.dto.UserTO;
import com.github.ticherti.voteforlunch.mapper.UserMapper;
import com.github.ticherti.voteforlunch.model.Role;
import com.github.ticherti.voteforlunch.model.User;
import com.github.ticherti.voteforlunch.repository.UserRepository;
import com.github.ticherti.voteforlunch.util.validation.Encoder;
import com.github.ticherti.voteforlunch.web.AuthUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    //todo Encoder goes to WebSecurity config thus should be diceded to be public in some util Class, not here. Or not.
    public static final PasswordEncoder PASSWORD_ENCODER = Encoder.PASSWORD_ENCODER;
    private final UserRepository userRepository;
    private final UserMapper mapper;

    public Optional<User> get(int id) {
        return userRepository.findById(id);
    }

    public Optional<User> getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    public void delete(int id) {
        userRepository.deleteExisted(id);
    }

    //    todo check out all the create-save methods throughout the project to be the same
    @Transactional
    @Modifying
    public User save(User user) {
        return userRepository.save(prepareToSave(user));
    }

    //todo statics should be upper. But this two are mapper responsibility
    public static User createNewFromTo(UserTO userTO) {
        return new User(null, userTO.getName(), userTO.getEmail().toLowerCase(), userTO.getPassword(), Role.USER);
    }

    public static User updateFromTo(User user, UserTO userTO) {
        user.setName(userTO.getName());
        user.setEmail(userTO.getEmail().toLowerCase());
        user.setPassword(userTO.getPassword());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        log.debug("Authenticating '{}'", email);
        Optional<User> optionalUser = getByEmail(email);
        return new AuthUser(optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("User '" + email + "' was not found")));
    }

    private static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
