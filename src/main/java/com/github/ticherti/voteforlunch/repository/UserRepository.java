package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}

