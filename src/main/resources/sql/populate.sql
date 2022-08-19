insert into bill_status (id, status)
values
    (1, 'ready'),
    (2, 'paid');

insert into card_status (id, status)
values
    (1, 'unblocked'),
    (2, 'blocked'),
    (3, 'ready to unblock');

insert into client_status (id, status)
values
    (1, 'unblocked'),
    (2, 'blocked');

insert into role (id, role)
values
    (1, 'admin'),
    (2, 'client');

insert into client (id, login, password, create_time, client_status_id, role_id)
values
    (DEFAULT, 'admin', 'admin', DEFAULT, 1, 1),
    (DEFAULT, 'user', 'user', DEFAULT, 1, 2),
    (DEFAULT, 'kolya', 'kolya', DEFAULT, 1, 2),
    (DEFAULT, 'sokima', 'sokima', DEFAULT, 1, 2),
    (DEFAULT, 'maverick', 'maverick', DEFAULT, 1, 2);

insert into card (id, name, balance, card_status_id, client_id)
VALUES
    (DEFAULT, '0001', 100, 1, (SELECT id FROM client where login = 'user')),
    (DEFAULT, '0002', 100, 1, (SELECT id FROM client where login = 'user')),
    (DEFAULT, '0003', 100, 1, (SELECT id FROM client where login = 'user'));

insert into bill (id, sum, date, card_id, bill_status_id)
VALUES
    (DEFAULT, 30, DEFAULT, 1, 1),
    (DEFAULT, 50, DEFAULT, 1, 1),
    (DEFAULT, 100, DEFAULT, 1, 1),
    (DEFAULT, 200, DEFAULT, 1, 1),
    (DEFAULT, 500, DEFAULT, 1, 1),
    (DEFAULT, 500, DEFAULT, 2, 1),
    (DEFAULT, 500, DEFAULT, 3, 1);