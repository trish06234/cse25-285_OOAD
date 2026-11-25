CREATE TABLE transactions (
    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_number TEXT NOT NULL,
    transaction_details TEXT NOT NULL,
    FOREIGN KEY(account_number)
        REFERENCES accounts(account_number)
);