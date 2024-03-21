CREATE TABLE IF NOT EXISTS tool (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tool_code VARCHAR(255) UNIQUE NOT NULL,
    tool_type VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    INDEX idx_tool_code (tool_code)
    );

CREATE TABLE IF NOT EXISTS checkout (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tool_code VARCHAR(255) NOT NULL,
    rental_day_count INT NOT NULL,
    discount_percent INT NOT NULL CHECK(discount_percent >= 0 AND discount_percent <= 100),
    check_out_date DATE NOT NULL,

    FOREIGN KEY (tool_code) REFERENCES tool(tool_code)
    );

INSERT INTO tool (tool_code, tool_type, brand) VALUES
       ('CHNS', 'Chainsaw', 'Stihl'),
       ('LADW', 'Ladder', 'Werner'),
       ('JAKD', 'Jackhammer', 'DeWalt'),
       ('JAKR', 'Jackhammer', 'Ridgid');
