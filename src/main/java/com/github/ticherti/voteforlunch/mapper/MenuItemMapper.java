package com.github.ticherti.voteforlunch.mapper;

import com.github.ticherti.voteforlunch.dto.MenuItemTO;
import com.github.ticherti.voteforlunch.model.MenuItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    MenuItem getEntity(MenuItemTO menuItemTO);

    MenuItemTO getDTO(MenuItem menuItem);

    List<MenuItemTO> getDTO(List<MenuItem> menuItems);
}
