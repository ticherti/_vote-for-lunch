package com.github.ticherti.voteforlunch.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@RequiredArgsConstructor
@Data
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MenuItemTO extends NamedTO {

    @NotNull
    private int price;

    private LocalDate date = LocalDate.now();
    @NotNull
    private int restaurantId;
}
