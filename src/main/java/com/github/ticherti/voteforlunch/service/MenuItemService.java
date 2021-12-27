package com.github.ticherti.voteforlunch.service;

import com.github.ticherti.voteforlunch.model.MenuItem;
import com.github.ticherti.voteforlunch.repository.MenuItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class MenuItemService {
    //    todo Add loggers everywhere
//    todo Add consistency checks
    private MenuItemRepository menuItemRepository;


    public MenuItem get(int restaurantId, int id) {
        return menuItemRepository.getById(id);
    }

    //todo find out where should it be sorted
    public List<MenuItem> getAll(int restaurantId, LocalDate date) {
        return menuItemRepository.getAllByRestaurantAndDate(restaurantId, date);
    }

    public MenuItem create(int restaurantId, MenuItem item) {
        return menuItemRepository.save(item);
    }
//todo add her date option. Date for All comes from controller. Should be the same.
    public void update(int restaurantId, MenuItem item) {
        menuItemRepository.save(item);
    }

    public void delete(int restaurantId, int id){
        menuItemRepository.deleteById(id);
    }
}
