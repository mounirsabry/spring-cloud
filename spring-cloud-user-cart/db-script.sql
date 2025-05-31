DROP DATABASE IF EXISTS spring_cloud_user_cart;

CREATE DATABASE spring_cloud_user_cart;

USE spring_cloud_user_cart;

CREATE TABLE normal_user (
	user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(32) NOT NULL
);

CREATE TABLE user_cart_item (
	user_id BIGINT NOT NULL,
	book_id BIGINT NOT NULL,
	PRIMARY KEY(user_id, book_id),
	CONSTRAINT user_cart_user_id
		FOREIGN KEY (user_id)
		REFERENCES normal_user(user_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT user_cart_book_id
		FOREIGN KEY (book_id)
		REFERENCES book(book_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);
