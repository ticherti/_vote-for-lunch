package com.github.ticherti.voteforlunch.mapper;

import com.github.ticherti.voteforlunch.dto.RestaurantTO;
import com.github.ticherti.voteforlunch.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant getEntity(RestaurantTO restaurantTO);

    @Mappings({@Mapping(target = "menuItems", ignore = true)})
    RestaurantTO getLazyDTO(Restaurant restaurant);

    RestaurantTO getEagerDTO(Restaurant restaurant);

    default List<RestaurantTO> getLazyDTO(List<Restaurant> restaurants) {
        if (restaurants == null) {
            return null;
        }
        List<RestaurantTO> list = new ArrayList<RestaurantTO>(restaurants.size());
        for (Restaurant restaurant : restaurants) {
            list.add(getLazyDTO(restaurant));
        }
        return list;
    }

    default List<RestaurantTO> getEagerDTO(List<Restaurant> restaurants) {
        if (restaurants == null) {
            return null;
        }
        List<RestaurantTO> list = new ArrayList<RestaurantTO>(restaurants.size());
        for (Restaurant restaurant : restaurants) {
            list.add(getEagerDTO(restaurant));
        }
        return list;
    }
}


