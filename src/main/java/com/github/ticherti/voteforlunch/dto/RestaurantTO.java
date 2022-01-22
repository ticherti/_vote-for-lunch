package com.github.ticherti.voteforlunch.dto;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTO extends NamedTO {

    public RestaurantTO(Integer id, String name) {
        super(id, name);
    }
}
