package com.github.ticherti.voteforlunch.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode //todo find out if I don't need this line. Check it with the tests
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
public class Restaurant extends NamedEntity {

    @OneToMany(mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    List<MenuItem> menuItems;
}
