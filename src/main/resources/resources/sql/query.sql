CREATE DATABASE IF NOT EXISTS theNewPharmacy;

USE theNewPharmacy;

CREATE TABLE IF NOT EXISTS Employee (
    employeeId VARCHAR(10) PRIMARY KEY,
    name VARCHAR(20),
    NICNo VARCHAR(20),
    address VARCHAR(50),
    tel VARCHAR(15),
    salary DECIMAL(10,2)
    );

CREATE TABLE IF NOT EXISTS customer (
    cuId VARCHAR(10) PRIMARY KEY,
    name VARCHAR(20),
    nicNo VARCHAR(20),
    address VARCHAR(50),
    tel VARCHAR(15)
    );

CREATE TABLE IF NOT EXISTS supplier (
    supplierId VARCHAR(10) PRIMARY KEY,
    name VARCHAR(25),
    description VARCHAR(50),
    address VARCHAR(50),
    tel VARCHAR(15)
    );

CREATE TABLE IF NOT EXISTS stock (
    stockId VARCHAR(10) PRIMARY KEY,
    description VARCHAR(50),
    category VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS supplierDetails (
    supplierId VARCHAR(10),
    FOREIGN KEY (supplierId) REFERENCES supplier(supplierId)  ON UPDATE CASCADE ON DELETE CASCADE,
    stockId VARCHAR(10),
    FOREIGN KEY (stockId) REFERENCES stock(stockId)  ON UPDATE CASCADE ON DELETE CASCADE,
    date DATE
    );

CREATE TABLE IF NOT EXISTS payment (
    payId VARCHAR(10) PRIMARY KEY,
    method VARCHAR(100),
    amount DECIMAL(10,2),
    date DATE
    );

CREATE TABLE IF NOT EXISTS item (
    itemId VARCHAR(10) PRIMARY KEY,
    description VARCHAR(50),
    unitPrice DECIMAL(10,2),
    qtyOnHand INT, -- Removed size specification for INT
    stockId VARCHAR(10),
    FOREIGN KEY (stockId) REFERENCES stock(stockId)  ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS orders (
    orderId VARCHAR(10) PRIMARY KEY,
    description VARCHAR(50),
    paymentAmount DECIMAL(10,2),
    date DATE,
    cuId VARCHAR(10),
    FOREIGN KEY (cuId) REFERENCES customer(cuId)  ON UPDATE CASCADE ON DELETE CASCADE,
    payId VARCHAR(10),
    FOREIGN KEY (payId) REFERENCES payment(payId)  ON UPDATE CASCADE ON DELETE CASCADE,
    employeeId VARCHAR(10),
    FOREIGN KEY (employeeId) REFERENCES Employee(employeeId)  ON UPDATE CASCADE ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS orderDetails (
    itemId VARCHAR(10),
    FOREIGN KEY (itemId) REFERENCES item(itemId)  ON UPDATE CASCADE ON DELETE CASCADE,
    orderId VARCHAR(10),
    FOREIGN KEY (orderId) REFERENCES orders(orderId)  ON UPDATE CASCADE ON DELETE CASCADE,
    qty INT, -- Removed size specification for INT
    unitPrice DECIMAL(10,2)
    );

CREATE TABLE IF NOT EXISTS users (
    userId VARCHAR(10) PRIMARY KEY,
    userName VARCHAR(20),
    password VARCHAR(10)
    );
