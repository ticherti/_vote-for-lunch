package com.github.ticherti.voteforlunch.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor //check if the same as top2 user
//add lombok get and set
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //check if the same as user
    private long id;
    private String name;
    private int price;
}
