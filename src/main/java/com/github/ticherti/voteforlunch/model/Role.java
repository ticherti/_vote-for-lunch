package com.github.ticherti.voteforlunch.model;

import org.springframework.security.core.GrantedAuthority;
//todo check out this
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    @Override
    public String getAuthority() {
        //   https://stackoverflow.com/a/19542316/548473
        return "ROLE_" + name();
    }
}