package com.github.ticherti.voteforlunch.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RequiredArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MenuItemTO extends NamedTO {

    @NotNull
    private final int price;

    private final LocalDate date;

    private final int restaurantId;
}
