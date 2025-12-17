# Employee Management System

This project is a simple employee management system built as part of an academic assignment. It consists of a Spring Boot backend and a React frontend. The system uses the MySQL/MariaDB employees database and exposes RESTful endpoints that return JSON.

The frontend and backend are separate projects located in the same repository.

---

Project structure

employee-frontend/

* React frontend
* Runs on port 3000

M7-P2/

* Spring Boot backend
* Runs on port 8080

---

Technology used

Backend

* Java
* Spring Boot
* Spring Data JPA (Hibernate)
* Maven
* MySQL / MariaDB

Frontend

* React
* JavaScript
* HTML and CSS

---

Backend setup

1. Ensure MySQL or MariaDB is running and the employees database is available.

2. Configure the database connection in application.properties:

# MariaDB connection
spring.datasource.url=jdbc:mariadb://localhost:3306/employees
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# JPA / Hibernate settings
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect


3. Run the backend:

mvn spring-boot:run

The backend will be available at:
[http://localhost:8080](http://localhost:8080)

---

Frontend setup

1. Navigate to the frontend directory:

cd employee-frontend

2. Install dependencies:

npm install

3. Start the development server:

npm start

The frontend will be available at:
[http://localhost:3000](http://localhost:3000)

---

CORS configuration

CORS is enabled in the backend to allow requests from the React frontend running on port 3000.

---

Available API endpoints

Endpoint 1: Get all departments
GET /api/departments
Returns department number and department name for all departments.

Endpoint 2: Get employee by employee number
GET /api/employees/{empNo}
Returns the full employee record for the given employee number.

Endpoint 3: Get employees by department
GET /api/departments/{deptNo}/employees?page=1
Returns a paginated list of employees for the given department.

Endpoint 4: Promote an employee
POST /api/employees/promote
Consumes a JSON request body containing employee number, new title, new salary, new department number and an optional promotion date.

---

Notes

* The backend must be running before the frontend is started.
* The default page number for paginated endpoints is 1.
* This project is intended for learning and assessment purposes.
