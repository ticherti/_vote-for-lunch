package com.github.ticherti.voteforlunch.dto;

import com.github.ticherti.voteforlunch.model.BaseEntity;
import com.github.ticherti.voteforlunch.model.Restaurant;
import com.github.ticherti.voteforlunch.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

//todo standartize here for all to's, start with user
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class VoteTO extends BaseEntity {
//    todo Add validity
    private LocalDate date = LocalDate.now();

    private int userId;

    private int restaurantId;
}
