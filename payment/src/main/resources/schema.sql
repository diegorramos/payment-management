CREATE TABLE payments (
    id VARCHAR(255) PRIMARY KEY,
    date DATE,
    status VARCHAR(255),
    amount NUMERIC,
    description VARCHAR (255),
    destination VARCHAR(255),
    created_at DATE,
    frequency VARCHAR(255),
    final_date DATE,
    next_date DATE
);