DROP DATABASE IF EXISTS spring_cloud_mono;

CREATE DATABASE spring_cloud_mono;

USE spring_cloud_mono;

CREATE TABLE normal_user (
	user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	email VARCHAR(50) NOT NULL,
	password VARCHAR(32) NOT NULL
);

CREATE TABLE book (
	book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	title VARCHAR(50) NOT NULL
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

CREATE TABLE user_order (
	order_id BIGINT AUTO_INCREMENT PRIMARY KEY,
	user_id BIGINT NOT NULL,
	total_price DOUBLE NOT NULL,
	CONSTRAINT user_order_user_id
		FOREIGN KEY (user_id)
		REFERENCES normal_user(user_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE order_item (
	order_id BIGINT NOT NULL,
	book_ID BIGINT NOT NULL,
	quantity INT NOT NULL,
	CONSTRAINT order_item_order_id
		FOREIGN KEY (order_id)
		REFERENCES user_order(order_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT order_item_book_id
		FOREIGN KEY (book_id)
		REFERENCES book(book_id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);
