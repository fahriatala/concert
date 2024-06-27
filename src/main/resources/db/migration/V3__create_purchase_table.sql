CREATE TABLE purchase (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ticket_id INT NOT NULL,
    user_id INT NULL COMMENT 'In real life this field should not be null',
    purchase_status ENUM('1', '2', '3', '4') NOT NULL COMMENT '1=booked, 2=paid, 3=expired, 4=canceled by user',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    FOREIGN KEY (ticket_id) REFERENCES ticket(id)
);