package com.github.ticherti.voteforlunch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantWithMenuTO extends NamedTO {

    List<MenuItemTO> menuItems;

    public RestaurantWithMenuTO(Integer id, String name, List<MenuItemTO> menuItems) {
        super(id, name);
        this.menuItems = menuItems;
    }
}
