# Mock Project: Backend Development with Java

## Overview
This project is a mock backend application built to manage and store company staff information.  
It implements REST endpoints using **JAX-RS**, database access with **JPA/Hibernate**, and is packaged with **Maven** for deployment on **Apache Tomcat**.  
The backend interfaces with the **MySQL Employees Sample Database** (running on MariaDB).

---

## Tech Stack
- **Java 21**
- **JAX-RS** (REST API)
- **Hibernate/JPA** (ORM)
- **MariaDB/MySQL Employees Sample Database**
- **Maven** (build & dependency management)
- **Apache Tomcat** (deployment)
- **Postman** (API testing)
- **Jackson** (JSON serialization)


---

## Project Structure
```
src/main/java/org/DigiCorp/
 ├── model/        # JPA entities
 ├── dto/          # DTOs for lean JSON responses
 ├── DAO/          # Business logic & queries
 └── service/      # JAX-RS endpoints

src/main/resources/
 └── META-INF/persistence.xml

pom.xml
```
## Endpoints
  
1. ```GET /api/employees/getAllDepartments```
   - Endpoint #1: Get All Departments
   - Returns all department names and department numbers.

2. ```GET /api/employees/getEmployeeRecord?empNo={empNo}```
   - Endpoint #2: Get Employee Record  
   - Returns the full employee record (including salaries, titles, deptEmp, deptManager).

3. ```GET /api/employees/getAllEmployeeRecords?departmentNo={deptNo}&page={page}```
   - Endpoint #3: Get Employee Records by Department  
   - Returns employee number, first name, last name, and hire date for employees in a department.  
   - Results are paginated (20 records per page).
   - Page query parameter optional, defaults to page 1.

4. ```POST /api/employees/promote ```
   - Endpoint #4: Promote Employee  
   - Consumes a JSON payload with promotion details.  
   - Promotions are processed one at a time.

## Setup Instructions
Clone the repository:
git clone https://github.com/pk-wu/M7-P2.git

Configure MariaDB with the Employees Sample Database.

(Database Source: https://dev.mysql.com/doc/employee/en/) 

Update persistence.xml with your DB credentials.

Build the project:
mvn clean package

Deploy the generated .war file to Apache Tomcat.  

Test endpoints using Postman.


## Notes
- Composite primary keys implemented using ```@IdClass```
- All responses return JSON with appropriate HTTP status codes
- Input validation assumes numeric inputs are clean but not always valid
- Project packaged as .war for deployment

## Author
Developed by pk-wu and LeanneLee97 for educational purposes
