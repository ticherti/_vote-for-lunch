package com.github.ticherti.voteforlunch.dto;

import lombok.*;

import java.util.List;

@Data
@ToString(callSuper = true)
//todo if I can use @Required instead
public class RestaurantTO extends NamedTO {
//todo add messages to validation
//    todo do I need validation if it's responce TO
//    todo ID comes from base TO. Dicide what's up with validation
//    private Integer id;

//    todo This comes from NamedTO. Delete if ok
//    @NotBlank
//    @Size(min = 2, max = 100)
////    @NoHtml
//    private String name;

    private List<MenuItemTO> menuItems;
}
