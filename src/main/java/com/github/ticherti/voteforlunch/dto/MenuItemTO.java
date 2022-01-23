package com.github.ticherti.voteforlunch.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MenuItemTO extends NamedTO {

    @PositiveOrZero
    @NotNull
    private int price;

    @NotNull
    private LocalDate date;

    public MenuItemTO(Integer id, String name, int price, LocalDate date) {
        super(id, name);
        this.price = price;
        this.date = date;
    }
}
