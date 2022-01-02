package com.github.ticherti.voteforlunch.dto;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
public class MenuItemTO extends NamedTO {

    private int price;

    private LocalDate date = LocalDate.now();

    private int restaurantId;
}
