CREATE TABLE IF NOT EXISTS users (
     id SERIAL PRIMARY KEY,
     username TEXT NOT NULL,
     balance NUMERIC DEFAULT 10000.00
);

-- INSERT INTO users (id, username, balance) VALUES (1, 'testuser', 10000.00);

CREATE TABLE IF NOT EXISTS holdings (
      id SERIAL PRIMARY KEY,
      user_id INT REFERENCES users(id),
      symbol TEXT NOT NULL,
      quantity NUMERIC NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
      id SERIAL PRIMARY KEY,
      user_id INT NOT NULL,
      transaction_type VARCHAR(10) NOT NULL, -- "buy" or "sell"
      currency VARCHAR(10) NOT NULL,
      amount DECIMAL(18, 8) NOT NULL,       -- Amount of cryptocurrency
      price DECIMAL(18, 8) NOT NULL,        -- Price per unit
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      total_cost DECIMAL(18,2)
);
