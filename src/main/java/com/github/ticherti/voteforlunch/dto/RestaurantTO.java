package com.github.ticherti.voteforlunch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RestaurantTO extends NamedTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<MenuItemTO> menuItems;

}
