CREATE TABLE IF NOT EXISTS tool (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tool_code VARCHAR(255) UNIQUE NOT NULL,
    tool_type VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    FOREIGN KEY (tool_type) REFERENCES tool_types(type),
    INDEX idx_tool_code (tool_code)
    );

INSERT INTO tool (tool_code, tool_type, brand) VALUES
    ('CHNS', 'Chainsaw', 'Stihl'),
    ('LADW', 'Ladder', 'Werner'),
    ('JAKD', 'Jackhammer', 'DeWalt'),
    ('JAKR', 'Jackhammer', 'Ridgid');
