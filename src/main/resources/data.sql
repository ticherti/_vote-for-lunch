INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANTS (name)
VALUES ('Central Park Café'),
       ('Deleting Joe');

INSERT INTO MENUITEMS (name, price, restaurant_id)
VALUES ('Burger with turkey', 400, 1),
       ('Beets with sesame', 200, 1),
       ('Bigburger', 300, 2),
       ('Cola', 50, 2);

INSERT INTO VOTES (RESTAURANT_ID, USER_ID, DATE)
VALUES (1, 1, '2022-01-02'),
       (2, 2, '2022-01-02');

INSERT INTO VOTES (RESTAURANT_ID, USER_ID)
VALUES (1,1),
       (2,2);