-- Brug databasen
USE wishlist;

-- Drop eksisterende tabeller hvis de findes (så scriptet kan køres igen uden fejl)
DROP TABLE IF EXISTS wishWishlist;
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS userWishlist;
DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS user;


-- Opret tabeller
CREATE TABLE user
(
    userID   INT AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(100) NOT NULL,
    password VARCHAR(10)
);

CREATE TABLE wishlist
(
    wishlistID INT AUTO_INCREMENT PRIMARY KEY,
    userID     INT,
    FOREIGN KEY (userID) REFERENCES user (userID)
);

CREATE TABLE userWishlist
(
    userID     INT,
    FOREIGN KEY (userID) references user (userID),

    wishlistID INT,
    FOREIGN key (wishlistID) references wishlist (wishlistID)
);

CREATE TABLE wish
(
    wishID      INT AUTO_INCREMENT PRIMARY KEY,

    wishlistID  INT,
    FOREIGN KEY (wishlistID) references wishlist (wishlistID),

    wishName    VARCHAR(250)  NOT NULL,
    description VARCHAR(150)  NOT NULL,
    link        VARCHAR(1000) NOT NULL,
    price       INT,
    isReserved  BOOLEAN
);

CREATE TABLE wishWishlist
(
    wishID     INT,
    FOREIGN KEY (wishID) references wish (wishID),

    wishlistID INT,
    FOREIGN KEY (wishlistID) references wishlist (wishlistID)
);