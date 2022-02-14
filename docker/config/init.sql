-- Creation of payments table
CREATE TABLE IF NOT EXISTS payments (
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

-- Creation of receipts table
CREATE TABLE IF NOT EXISTS receipts (
    id VARCHAR(255) PRIMARY KEY,
    status VARCHAR(255),
    payment_id VARCHAR(255),
    amount NUMERIC,
    destination VARCHAR(255),
    payment_date DATE
);