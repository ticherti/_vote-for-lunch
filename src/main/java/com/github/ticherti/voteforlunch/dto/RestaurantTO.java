package com.github.ticherti.voteforlunch.dto;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RestaurantTO extends NamedTO {

    private List<MenuItemTO> menuItems;

    public RestaurantTO(Integer id, String name, List<MenuItemTO> menuItems) {
        super(id, name);
        this.menuItems = menuItems;
    }
}
