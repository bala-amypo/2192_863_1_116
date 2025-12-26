# Database Setup Instructions

## Prerequisites
1. Install MySQL Server 8.0 or higher
2. Start MySQL service

## Setup Steps

### 1. Create Database and User
```sql
-- Connect to MySQL as root
mysql -u root -p

-- Create database
CREATE DATABASE IF NOT EXISTS contract_breach_db;

-- Create user (optional, or use root)
CREATE USER 'contract_user'@'localhost' IDENTIFIED BY 'contract_password';
GRANT ALL PRIVILEGES ON contract_breach_db.* TO 'contract_user'@'localhost';
FLUSH PRIVILEGES;
```

### 2. Run Schema Script
```bash
mysql -u root -p contract_breach_db < database_schema.sql
```

### 3. Update Application Properties
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/contract_breach_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```

### 4. Run Application
```bash
mvn spring-boot:run
```

## API Endpoints
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- API Docs: http://localhost:8080/api-docs

## Default Login
- Email: admin@example.com
- Password: password

## Test Data
The schema includes sample contracts and a default breach rule for testing.