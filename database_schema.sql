-- Contract Breach Penalty Calculator Database Schema

USE parent;

-- Contracts table
CREATE TABLE contracts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_number VARCHAR(255) UNIQUE NOT NULL,
    title VARCHAR(255),
    counterparty_name VARCHAR(255),
    agreed_delivery_date DATE,
    base_contract_value DECIMAL(19,2),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Delivery records table
CREATE TABLE delivery_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT,
    delivery_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (contract_id) REFERENCES contracts(id)
);

-- Breach rules table
CREATE TABLE breach_rules (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    rule_name VARCHAR(255) UNIQUE,
    penalty_per_day DECIMAL(19,2),
    max_penalty_percentage DOUBLE,
    active BOOLEAN DEFAULT TRUE,
    is_default_rule BOOLEAN DEFAULT FALSE
);

-- Penalty calculations table
CREATE TABLE penalty_calculations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT,
    days_delayed INT,
    calculated_penalty DECIMAL(19,2),
    applied_rule_id BIGINT,
    calculated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (contract_id) REFERENCES contracts(id),
    FOREIGN KEY (applied_rule_id) REFERENCES breach_rules(id)
);

-- Breach reports table
CREATE TABLE breach_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    contract_id BIGINT,
    report_generated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    days_delayed INT,
    penalty_amount DECIMAL(19,2),
    remarks TEXT,
    FOREIGN KEY (contract_id) REFERENCES contracts(id)
);

-- Users table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- User roles table
CREATE TABLE user_roles (
    user_id BIGINT,
    role VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Vendor tiers table
CREATE TABLE vendor_tiers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tier_name VARCHAR(255),
    min_score_threshold INT,
    active BOOLEAN DEFAULT TRUE
);

-- Insert default breach rule
INSERT INTO breach_rules (rule_name, penalty_per_day, max_penalty_percentage, active, is_default_rule) 
VALUES ('DEFAULT_RULE', 50.00, 10.0, TRUE, TRUE);

-- Insert sample data for testing
INSERT INTO contracts (contract_number, title, counterparty_name, agreed_delivery_date, base_contract_value, status) 
VALUES 
('C-001', 'Software Development Contract', 'Tech Corp', '2024-01-15', 10000.00, 'ACTIVE'),
('C-002', 'Hardware Supply Contract', 'Hardware Inc', '2024-02-20', 25000.00, 'ACTIVE');

INSERT INTO users (email, password) 
VALUES ('admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.');
-- Password is 'password' encoded with BCrypt

INSERT INTO user_roles (user_id, role) 
VALUES (1, 'ROLE_ADMIN'), (1, 'ROLE_USER');