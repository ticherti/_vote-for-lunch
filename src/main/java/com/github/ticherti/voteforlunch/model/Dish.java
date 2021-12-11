package com.github.ticherti.voteforlunch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@NoArgsConstructor
public class Dish extends NamedEntity {

    @Column(name = "price", columnDefinition = "int default 0")
    private int price;

    @Column(name = "date", columnDefinition = "date default current_date")
    private LocalDate date = LocalDate.now();

    //    todo Find out if votes or dishes should be cleared if restaurant or user are deleted
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public Dish(String name, int price, LocalDate date, Restaurant restaurant) {
        this(null, name, price, date, restaurant);
    }

    public Dish(Integer id, String name, int price, LocalDate date, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                ", restaurant=" + restaurant.name +
                '}';
    }
}
