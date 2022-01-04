package com.github.ticherti.voteforlunch.dto;

import lombok.*;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTO extends NamedTO {

    private List<MenuItemTO> menuItems;
}
