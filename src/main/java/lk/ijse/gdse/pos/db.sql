CREATE DATABASE Pos;

USE Pos;

CREATE TABLE Customer(
    c_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(255),
    mobile INT(10)
);

CREATE TABLE Item(
    i_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(100),
    price INT(20),
    qty INT(50),
    category VARCHAR(100),
    image VARCHAR(255)
)
