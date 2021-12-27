package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
