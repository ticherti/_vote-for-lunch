package com.github.ticherti.voteforlunch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "votes")
@Getter
@Setter
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "date", columnDefinition = "date default current_date")
    private LocalDate date = LocalDate.now();

    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @Column(name = "restaurant", nullable = false)
    private Restaurant restaurant;

    public Vote(LocalDate date, User user, Restaurant restaurant) {
        this(null, date, user, restaurant);
    }

    public Vote(Integer id, LocalDate date, User user, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.user = user;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", date=" + date +
                ", user=" + user.name +
                ", restaurant=" + restaurant.name +
                '}';
    }
}
