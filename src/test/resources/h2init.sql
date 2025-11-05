-- Drop eksisterende tabeller hvis de findes (så scriptet kan køres igen uden fejl)
CREATE SCHEMA IF NOT EXISTS wishlist;
SET SCHEMA wishlist;

DROP TABLE IF EXISTS sharedWishlist;
DROP TABLE IF EXISTS wishWishlist;
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS userWishlist;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS `user`;


-- Opret tabeller
CREATE TABLE `user`
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
    FOREIGN KEY (userID) REFERENCES `user` (userID) ON DELETE CASCADE
);

CREATE TABLE userWishlist
(
    userID     INT,
    FOREIGN KEY (userID) references `user` (userID) ON DELETE CASCADE,

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
    FOREIGN KEY (userID) REFERENCES `user`(userID) ON DELETE CASCADE

);

INSERT INTO `user` (userName, password)
VALUES ('Test User', '1234'),
       ('Test User 2', 'abcd'),

INSERT INTO wishlist (userID, wishlistName)
VALUES (1, 'Test wishlist'),
VALUES (2, 'Test wishlist 2'),

INSERT INTO userWishlist (userID, wishlistID)
VALUES (1, 1),
VALUES (2, 2),


-- Test data
INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (1, 'Test wish', 'Test wish description', 'test.dk', 2500, FALSE),

INSERT INTO wish (wishlistID, wishName, description, link, price, isReserved)
VALUES (2, 'Test wish 2', 'Test wish description 2', 'test2.dk', 800, FALSE),


INSERT INTO sharedWishlist (userID, wishlistID)
VALUES (1, 2),