package com.github.ticherti.voteforlunch.dto;

import com.github.ticherti.voteforlunch.HasId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
//todo decide what's up with access
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Data
//todo When I receive answer, I get isNew to print. I don't want it
public abstract class BaseTO implements HasId {
    //    todo decide if i need it
//    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    protected Integer id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
