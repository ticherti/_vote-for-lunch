package com.github.ticherti.voteforlunch.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.ticherti.voteforlunch.model.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTO extends BaseEntity {

    private LocalDate date = LocalDate.now();

    private int userId;

    @NotNull
    private int restaurantId;

    public VoteTO(int id, int userId, @NotNull int restaurantId) {
        super(id);
        this.userId = userId;
        this.restaurantId = restaurantId;
    }
}
