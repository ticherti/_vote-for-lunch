package com.github.ticherti.voteforlunch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Slf4j
@Entity
@Table(name = "menuitems", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "date", "name"},
        name = "menuitems_unique_restaurant_datetime_name_idx")})
public class MenuItem extends NamedEntity {

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "date", columnDefinition = "date default current_date")
    private LocalDate date = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;
}
