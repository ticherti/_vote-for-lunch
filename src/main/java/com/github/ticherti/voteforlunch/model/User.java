package com.github.ticherti.voteforlunch.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.ticherti.voteforlunch.HasIdAndEmail;
import com.github.ticherti.voteforlunch.util.validation.NoHtml;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
//Еще - объект AuthorizedUser будет хранится в сессии (про нее видео ниже) и для этого ему требуется сериализация
// средствами Java. Это наследование его и всех классов-полей от маркерного интерфейса Serializable и необязательный,
// но желательный serialVersionUID.
//        Будьте внимательны в выпускных проектах с Serializable. Им нужно помечать ТОЛЬКО объекты, которые будут
//        храниться в сессии
//При некоторых условиях Tomcat сохраняет данные сессии и ему требуется возможность их сериализации,
// поэтому объекты в сеcсии (и объекты, которые в них содержатся) обязательно должны имплементировать
// интерфейс Serializable (в нашем случае AuthorizedUser и UserTo).
//Все, что включает в себя сериализуемая сущность должно уметь сериализоваться, таким образом Если в Юзеере есть ЮзерТО,
//то юзерТо тоже должно юбыть сериалайзебл
//В прошлых выпусках я сам реализовывал интерфейс UserDetails. Сейчас я считаю проще отнаследовать AuthorizedUser
// от org.springframework.security.core.userdetails.User, который уже имеет реализацию. А в UserService мы реализуем
// UserDetailsService#loadUserByUsername и указываем этот сервис в spring-security.xml
// <authentication-provider user-service-ref="userService">. Также есть его стандартные реализации, которые
// использовались до нашей кастомной UserService, например jdbc-user-service использует реализацию JdbcUserDetailsManager

//todo Spring Binding lesson 9. Check out also userTo
//todo check out storing in DB email in lowercase
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
//        (access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"password"})
public class User extends NamedEntity implements HasIdAndEmail, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    @NoHtml   // https://stackoverflow.com/questions/17480809
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 100)
    // https://stackoverflow.com/a/12505165/548473
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date registered = new Date();

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}, name = "uk_user_roles"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Vote> votes;


    public User(User u) {
        this(u.id, u.name, u.email, u.password, u.enabled, u.registered, u.roles);
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, true, new Date(), EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, boolean enabled, Date registered, Collection<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}

