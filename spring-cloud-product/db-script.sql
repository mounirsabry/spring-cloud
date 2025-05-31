DROP DATABASE IF EXISTS spring_cloud_product;

CREATE DATABASE spring_cloud_product;

USE spring_cloud_product;

CREATE TABLE book (
	book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL
);
