CREATE DATABASE Pos;

USE Pos;

CREATE TABLE customer (
    c_Id       VARCHAR(30) PRIMARY KEY,
    firstName  VARCHAR(30),
    address    VARCHAR(30),
    mobile     VARCHAR(30)
);

CREATE TABLE item (
    i_Id      VARCHAR(30) PRIMARY KEY,
    itemName  VARCHAR(30),
    price     DOUBLE(10, 2),
    quantity  INT,
    category  VARCHAR(30),
    imagePath VARCHAR(100)
);

CREATE TABLE orders (
    orderId     VARCHAR(30) PRIMARY KEY,
    dateAndTime TIMESTAMP,
    customerId  VARCHAR(30),
    subtotal    DOUBLE(10, 2) DEFAULT 0.00,
    discount    DOUBLE(10, 2) DEFAULT 0.00,
    amount_paid DOUBLE(10, 2) DEFAULT 0.00,
    FOREIGN KEY (customerId) REFERENCES customer (c_Id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE orderDetail (
    orderId   VARCHAR(30),
    itemId    VARCHAR(30),
    quantity  VARCHAR(30),
    unitPrice DOUBLE(10, 2),
    FOREIGN KEY (orderId) REFERENCES orders (orderId)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (itemId) REFERENCES item (i_Id)
        ON UPDATE CASCADE ON DELETE CASCADE
);