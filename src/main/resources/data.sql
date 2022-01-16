INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Central Park Café'),
       ('Deleting Joe');

INSERT INTO MENU_ITEM (name, price, restaurant_id)
VALUES ('Burger with turkey', 400, 1),
       ('Beets with sesame', 200, 1),
       ('Bigburger', 300, 2),
       ('Cola', 50, 2);

INSERT INTO MENU_ITEM (name, price, restaurant_id, posted)
VALUES ('Fried cheese', 350, 1, '2015-06-21'),
       ('Carrot tart', 250, 1, '2015-06-21'),
       ('Green eggs and ham', 450, 2, '2015-06-21'),
       ('Cafe con cana', 150, 2, '2015-06-21');

INSERT INTO VOTE (restaurant_id, user_id, voted)
VALUES (1, 1, '2022-01-02'),
       (2, 2, '2022-01-02');

INSERT INTO VOTE (restaurant_id, user_id)
VALUES (1,1);