package com.github.ticherti.voteforlunch.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MenuItemTO extends NamedTO {

    @PositiveOrZero
    @NotNull
    private int price;

    private LocalDate date = LocalDate.now();

    @NotNull
    private int restaurantId;

    public MenuItemTO(Integer id, String name, int price, int restaurantId) {
        super(id, name);
        this.price = price;
        this.restaurantId = restaurantId;
    }
}
