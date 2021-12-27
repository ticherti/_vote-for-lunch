package com.github.ticherti.voteforlunch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class MenuItem extends NamedEntity {

//todo probably need to add unique to name-idRes-date ties

    @Column(name = "price", nullable = false, columnDefinition = "int default 0")
    private int price;

    @Column(name = "date", columnDefinition = "date default current_date")
    private LocalDate date = LocalDate.now();

    //    todo Find out if votes or dishes should be cleared if restaurant or user are deleted
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @ToString.Exclude
    private Restaurant restaurant;
}
