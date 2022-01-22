package com.github.ticherti.voteforlunch.dto;

import lombok.*;

@RequiredArgsConstructor
@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class RestaurantTO extends NamedTO {

    public RestaurantTO(Integer id, String name) {
        super(id, name);
    }
}
