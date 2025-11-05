-- Drop eksisterende tabeller hvis de findes (så scriptet kan køres igen uden fejl)
DROP TABLE IF EXISTS sharedWishlist;
DROP TABLE IF EXISTS wishWishlist;
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS userWishlist;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS `user`;


-- Opret tabeller
CREATE TABLE user
(
    userID   INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100) NOT NULL,
    password VARCHAR(10)
);

CREATE TABLE wishlist
(
    wishlistID   INT AUTO_INCREMENT PRIMARY KEY,
    wishlistName VARCHAR(250),
    userID       INT,
    FOREIGN KEY (userID) REFERENCES user (userID) ON DELETE CASCADE
);

CREATE TABLE userWishlist
(
    userID     INT,
    FOREIGN KEY (userID) references user (userID) ON DELETE CASCADE,

    wishlistID INT,
    FOREIGN key (wishlistID) references wishlist (wishlistID) ON DELETE CASCADE
);

CREATE TABLE wish
(
    wishID      INT AUTO_INCREMENT PRIMARY KEY,

    wishlistID  INT,
    FOREIGN KEY (wishlistID) references wishlist (wishlistID) ON DELETE CASCADE,

    wishName    VARCHAR(250)  NOT NULL,
    description VARCHAR(150)  NOT NULL,
    link        VARCHAR(1000) NOT NULL,
    price       INT,
    isReserved  BOOLEAN

);

CREATE TABLE wishWishlist
(
    wishID     INT,
    FOREIGN KEY (wishID) references wish (wishID) ON DELETE CASCADE,

    wishlistID INT,
    FOREIGN KEY (wishlistID) references wishlist (wishlistID) ON DELETE CASCADE
);

CREATE TABLE sharedWishlist (
                                wishlistID INT,
                                FOREIGN KEY (wishlistID) REFERENCES wishlist(wishlistID) ON DELETE CASCADE,

                                userID INT,
                                FOREIGN KEY (userID) REFERENCES user(userID) ON DELETE CASCADE

);

-- ---------------------------------------------------------
-- 🔹 Indsæt brugere
-- ---------------------------------------------------------
INSERT INTO user (userName, password)
VALUES ('Simon', '1234'),
       ('Martin', 'abcd'),
       ('Joshua', 'pass');

-- ---------------------------------------------------------
-- 🔹 Indsæt ønskelister (2 pr. bruger)
-- ---------------------------------------------------------
INSERT INTO wishlist (userID, wishlistName)
VALUES (1, 'Juleliste'),
       (1, 'Fødselsdagsliste'),        -- Simon’s lister
       (2, 'Barnedåb'),
       (2, 'Første skridtønskeliste'), -- Martin’s lister
       (3, 'Amagerminoen'),
       (3, 'Mount Everest grej');
-- Joshua’s lister

-- ---------------------------------------------------------
-- 🔹 Knyt brugere og ønskelister sammen
-- ---------------------------------------------------------
INSERT INTO userWishlist (userID, wishlistID)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 4),
       (3, 5),
       (3, 6);

-- ---------------------------------------------------------
-- 🔹 Indsæt ønsker (3 pr. ønskeliste = 18 total)
-- ---------------------------------------------------------

-- Simon’s første ønskeliste (wishlistID = 1)
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (1, 'Nintendo Switch', 'Håndholdt spillekonsol', 'https://www.nintendo.com/switch', 2500, FALSE),
       (1, 'Noise Cancelling Headphones', 'Sony WH-1000XM5', 'https://www.sony.com', 2800, TRUE),
       (1, 'Gaming Mouse', 'Logitech G Pro X Superlight', 'https://www.logitech.com', 1000, FALSE);

-- Simon’s anden ønskeliste (wishlistID = 2)
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (2, 'Weekendophold', 'Hotel i København for 2 personer', 'https://smallhotels.dk', 1500, FALSE),
       (2, 'Running Shoes', 'Nike Air Zoom Pegasus', 'https://www.nike.com', 1200, TRUE),
       (2, 'Smartwatch', 'Apple Watch SE', 'https://apple.com', 2600, FALSE);

-- Martin’s første ønskeliste (wishlistID = 3)
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (3, 'Mechanical Keyboard', 'RGB tastatur fra Keychron', 'https://www.keychron.com', 1100, FALSE),
       (3, 'Bluetooth Speaker', 'JBL Charge 5', 'https://www.jbl.com', 1200, FALSE),
       (3, 'Smart Lamp', 'Philips Hue bordlampe', 'https://www.philips-hue.com', 900, TRUE);

-- Martin’s anden ønskeliste (wishlistID = 4)
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (4, 'Coffee Grinder', 'Elektrisk kaffekværn', 'https://www.wilfa.dk', 800, FALSE),
       (4, 'Cooking Class', 'Madlavningskursus i Aarhus', 'https://madkursus.dk', 1000, FALSE),
       (4, 'Fitness Membership', '12 måneders medlemskab', 'https://fitnessworld.dk', 2999, TRUE);

-- Joshua’s første ønskeliste (wishlistID = 5)
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (5, 'Drone', 'DJI Mini 3 Pro', 'https://www.dji.com', 5500, FALSE),
       (5, 'Camera Lens', 'Canon EF 50mm f/1.8 STM', 'https://www.canon.dk', 900, TRUE),
       (5, 'Tripod', 'Kompakt stativ', 'https://www.manfrotto.com', 700, FALSE);

-- Joshua’s anden ønskeliste (wishlistID = 6)
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (6, 'Guitar', 'Akustisk Taylor 214ce', 'https://www.thomann.de', 9500, TRUE),
       (6, 'Music Lessons', '10 lektioner i guitar', 'https://musikskolen.dk', 2500, FALSE),
       (6, 'Concert Tickets', 'Billetter til Roskilde Festival', 'https://roskilde-festival.dk', 2800, FALSE);


INSERT INTO sharedWishlist (userID, wishlistID)
VALUES (3, 1),
       (3, 2);