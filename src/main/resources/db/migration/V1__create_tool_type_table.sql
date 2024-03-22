CREATE TABLE tool_types (
    type VARCHAR(255) NOT NULL,
    daily_rental_charge DECIMAL(10, 2) NOT NULL,
    weekday_charge BOOLEAN NOT NULL,
    weekend_charge BOOLEAN NOT NULL,
    holiday_charge BOOLEAN NOT NULL,
    PRIMARY KEY (Type)
);

INSERT INTO tool_types (type, daily_rental_charge, weekday_charge, weekend_charge, holiday_charge) VALUES
    ('Ladder', 1.99, TRUE, TRUE, FALSE),
    ('Chainsaw', 1.49, TRUE, FALSE, TRUE),
    ('Jackhammer', 2.99, TRUE, FALSE, FALSE);