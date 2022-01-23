package com.github.ticherti.voteforlunch.dto;

import com.github.ticherti.voteforlunch.util.validation.NoHtml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NamedTO extends BaseTO {
    @NotBlank
    @Size(min = 2, max = 100)
    @NoHtml
    protected String name;

    public NamedTO(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString() + '[' + name + ']';
    }
}
