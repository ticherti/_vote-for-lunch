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
@Entity
@Table(name = "menu_item", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "posted", "name"},
        name = "menu_item_unique_restaurant_postdate_name_idx")})
public class MenuItem extends NamedEntity {

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "posted", columnDefinition = "date default current_date", nullable = false)
    private LocalDate date = LocalDate.now();

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;
}
