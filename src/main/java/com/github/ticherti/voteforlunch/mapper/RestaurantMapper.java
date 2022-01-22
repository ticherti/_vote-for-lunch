package com.github.ticherti.voteforlunch.mapper;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.dto.RestaurantWithMenuTO;
import com.github.ticherti.voteforlunch.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MenuItemMapper.class)
public interface RestaurantMapper {

    Restaurant getEntity(RestaurantTO restaurantTO);

    RestaurantTO getDTO(Restaurant restaurant);

    RestaurantWithMenuTO getWithMenuDTO(Restaurant restaurant);

    List<RestaurantTO> getDTO(List<Restaurant> restaurants);

    List<RestaurantWithMenuTO> getWithMenuDTO(List<Restaurant> restaurants);
}


