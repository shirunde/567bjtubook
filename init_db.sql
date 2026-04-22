CREATE DATABASE IF NOT EXISTS secondhand_book CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE secondhand_book;

CREATE TABLE IF NOT EXISTS users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(50) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address VARCHAR(255),
    role INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(50) NOT NULL,
    isbn VARCHAR(20),
    price DECIMAL(10,2) NOT NULL,
    description TEXT,
    status INT DEFAULT 0,
    user_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    contact VARCHAR(100) NOT NULL,
    version VARCHAR(50),
    type VARCHAR(50),
    college VARCHAR(100),
    major VARCHAR(100),
    grade VARCHAR(20),
    image_url VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT,
    buyer_id INT,
    seller_id INT,
    price DECIMAL(10,2) NOT NULL,
    status INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (buyer_id) REFERENCES users(id),
    FOREIGN KEY (seller_id) REFERENCES users(id)
);
