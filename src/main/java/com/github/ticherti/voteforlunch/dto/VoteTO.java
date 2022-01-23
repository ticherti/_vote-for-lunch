package com.github.ticherti.voteforlunch.dto;

import com.github.ticherti.voteforlunch.model.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTO extends BaseEntity {

    LocalDate date;

    int userId;

    int restaurantId;

    public VoteTO(Integer id, LocalDate date, int userId, int restaurantId) {
        super(id);
        this.date = date;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
