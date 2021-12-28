package com.github.ticherti.voteforlunch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

public interface HasId {

    Integer getId();

    void setId(Integer id);
//todo check this commented field
//    @JsonIgnore
    default boolean isNew() {
        return getId() == null;
    }

    // doesn't work for hibernate lazy proxy
    default int id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
