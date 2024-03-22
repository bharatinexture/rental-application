CREATE TABLE IF NOT EXISTS rental_agreement (
    rental_agreement_number INT AUTO_INCREMENT PRIMARY KEY,
    tool_code VARCHAR(255) NOT NULL,
    tool_type VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    rental_days INT NOT NULL,
    checkout_date DATE NOT NULL,
    due_date DATE NOT NULL,
    daily_rental_charge DECIMAL(10, 2) NOT NULL,
    charge_days INT NOT NULL,
    pre_discount_charge DECIMAL(10, 2) NOT NULL,
    discount_percent INT NOT NULL,
    discount_amount DECIMAL(10, 2) NOT NULL,
    final_charge  DECIMAL(10, 2) NOT NULL
);