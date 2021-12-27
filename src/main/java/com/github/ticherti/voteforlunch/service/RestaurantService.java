package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {
//    todo Check out if I shold change that for be more simple to upgrade the app.
//     Make service return something instead of void
    private RestaurantRepository restaurantRepository;

    public Restaurant get(int id) {
       return restaurantRepository.getById(id);
    }
    public Restaurant getWithMenu(int id) {
        return restaurantRepository.getWithMenus(id, LocalDate.now());
    }
    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }
    public List<Restaurant> getAllWithMenus(){
        return restaurantRepository.getAllWithMenus(LocalDate.now());
    }

    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }
//   todo Check out what's up with deleteById method in meal or users. Why add a query method to a repo

    public void delete(int id){
        restaurantRepository.deleteById(id);
    }
}
