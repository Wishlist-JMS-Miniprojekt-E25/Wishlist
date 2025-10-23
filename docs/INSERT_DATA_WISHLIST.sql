-- Brugere
INSERT INTO user (userName, password)
VALUES ('Simon', '1234'),
       ('Martin', 'abcd'),
       ('Joshua', 'pass');

-- Wishlister (en pr. bruger)
INSERT INTO wishlist (userID)
VALUES (1), -- Simon
       (2), -- Martin
       (3); -- Joshua

-- Relation mellem brugere og ønskelister
INSERT INTO userWishlist (userID, wishlistID)
VALUES (1, 1),
       (2, 2),
       (3, 3);

-- Ønsker
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (1, 'Nintendo Switch', 'Håndholdt spillekonsol', 'https://www.nintendo.com/switch', 2500, FALSE),
       (1, 'Headphones', 'Sony WH-1000XM5 støjreducerende headset', 'https://www.sony.com', 2800, TRUE),
       (2, 'iPad Air', 'Apple iPad Air 10.9”', 'https://apple.com/ipad-air', 4500, FALSE),
       (2, 'Weekendophold', 'Hotelophold for 2 personer', 'https://smallhotels.dk', 1200, FALSE),
       (3, 'Gaming Chair', 'Ergonomisk gamer stol', 'https://www.dxracer.com', 1800, TRUE),
       (3, 'Keyboard', 'RGB mekanisk tastatur', 'https://www.logitech.com', 900, FALSE);

-- Relation mellem ønsker og ønskelister
INSERT INTO wishWishlist (wishID, wishlistID)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 3),
       (6, 3);