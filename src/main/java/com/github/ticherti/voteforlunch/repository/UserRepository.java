package com.github.ticherti.voteforlunch.repository;

import com.github.ticherti.voteforlunch.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}
//    todo https://www.youtube.com/watch?v=GOtoXLvg_IQ&list=PLlsMRoVt5sTNpihn4q4S4an2xpYkSA3RR&index=3  26 минута
//    todo add User everything . Up here is how Suleimanov do checks

