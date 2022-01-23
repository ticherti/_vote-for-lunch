package com.github.ticherti.voteforlunch.web.user;

import com.github.ticherti.voteforlunch.model.User;
import com.github.ticherti.voteforlunch.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import static com.github.ticherti.voteforlunch.util.validation.ValidationUtil.*;

@Slf4j
public abstract class AbstractUserController {
    public static final PasswordEncoder PASSWORD_ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    protected UserRepository userRepository;

    @Autowired
    private UniqueMailValidator emailValidator;

    public User save(User user) {
        log.info("Saving a user");
        checkNew(user);
        return prepareAndSave(user);
    }

    public void update(User user, int id) {
        log.info("Updating a user with id {}", id);
        assureIdConsistent(user, id);
        getFound(userRepository.findById(id));
        prepareAndSave(user);
    }

    protected void delete(int id) {
        log.info("Deleting a user with id {}", id);
        userRepository.deleteExisted(id);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    @Transactional
    @Modifying
    protected User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }
}