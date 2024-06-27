CREATE TABLE ticket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    concert_id INT NOT NULL,
    ticket_class VARCHAR(255) NOT NULL,
    ticket_total INT NOT NULL,
    ticket_amount DECIMAL(10, 2) NOT NULL,
    ticket_purchase_start DATETIME NULL,
    ticket_purchase_end DATETIME NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL,
    FOREIGN KEY (concert_id) REFERENCES concert(id)
);