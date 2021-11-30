INSERT INTO users (id, first_name, last_name, username, email, address, password, cvu_mercado_pago, wallet_address, reputation, points)
VALUES (1, 'Admin', 'Admin', 'backenddesappapi', 'test@mail.com', 'anAddress',
        '$2a$10$XcXvkn6BOhRR4RdMjhVw9usqnvDfhaSLhnh6lj.QD.SJWJMFzxRB6', 7676730811100056207661, 123456789, 0, 0),
       (2, 'Nahuel', 'Pereyra', 'nahuel', 'nahuelmpereyra@gmail.com', 'A fake address',
        '$2a$10$RUuvKxxYP.k1BjAHEWZ/HOIfqDHs9uGBklfEZTA6lFlYd7BcKCQyi', 0148061611100099363142, 234567890, 0, 0),
       (3, 'Daniela', 'Roman', 'daniela', 'daniela@mail.com', 'another Address',
        '$2a$10$RUuvKxxYP.k1BjAHEWZ/HOIfqDHs9uGBklfEZTA6lFlYd7BcKCQyi', 9825281311100040127324, 567891234, 0, 0),
       (4, 'Franco', 'Pereyra', 'franco', 'franco@mail.com', 'an Address',
        '$2a$10$RUuvKxxYP.k1BjAHEWZ/HOIfqDHs9uGBklfEZTA6lFlYd7BcKCQyi', 9373216311100031340523, 987654321, 0, 0);

INSERT INTO cripto_currency (id, name, symbol)
VALUES (1, 'ALICE', 'ALICEUSDT'),
       (2, 'MATIC', 'MATICUSDT'),
       (3, 'AXS', 'AXSUSDT'),
       (4, 'AAVE', 'AAVEUSDT'),
       (5, 'ATOM', 'ATOMUSDT'),
       (6, 'NEOU', 'NEOUSDT'),
       (7, 'DOT', 'DOTUSDT'),
       (8, 'ETH', 'ETHUSDT'),
       (9, 'CAKE', 'CAKEUSDT'),
       (10, 'BTC', 'BTCUSDT'),
       (11, 'BNB', 'BNBUSDT'),
       (12, 'ADA', 'ADAUSDT'),
       (13, 'TRX', 'TRXUSDT'),
       (14, 'AUDIO', 'AUDIOUSDT');


INSERT INTO transactions (id, initial_type, quantity, sell_price, state, cripto, user_buyer, user_seller, created_date)
VALUES (1, 'COMPRA', 10.0, 1000.0, 'Open', 3, 2, null, '2021-11-30 01:55:48.204002'),
       (2, 'VENTA', 10.0, 1500.0, 'Open', 5, null, 3, '2021-11-30 01:55:48.204002'),
       (3, 'COMPRA', 5.0, 50000.0, 'Open', 2, 2, null, '2021-11-30 01:55:48.204002'),
       (4, 'COMPRA', 10.0, 3508.0, 'Open', 8, 3, null, '2021-11-30 01:55:48.204002'),
       (5, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 2, '2021-11-30 01:55:48.204002'),
       (6, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 2, '2021-11-30 01:55:48.204002'),
       (7, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 3, '2021-11-30 01:55:48.204002'),
       (8, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 2, '2021-11-30 01:55:48.204002'),
       (9, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 4, '2021-11-30 01:55:48.204002'),
       (10, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 2, '2021-11-30 01:55:48.204002'),
       (11, 'VENTA', 22.0, 4207.0, 'Open', 10, null, 2, '2021-11-30 01:55:48.204002'),
       (12, 'COMPRA', 15.0, 7502.0, 'Open', 2, 4, null, '2021-11-30 01:55:48.204002');



