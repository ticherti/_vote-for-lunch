package com.github.ticherti.voteforlunch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTO extends NamedTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MenuItemTO> menuItems;
}
