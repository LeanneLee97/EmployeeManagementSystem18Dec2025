package org.DigiCorp.service;

import org.DigiCorp.dto.EmployeePromotionRequest;
import org.DigiCorp.dto.EmployeeRecordDTO;
import org.DigiCorp.exceptions.InvalidDataException;
import org.DigiCorp.model.Department;
import org.DigiCorp.util.Helper;
import org.DigiCorp.model.Employee;
import org.DigiCorp.dao.EmployeeDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Class defines the REST endpoints.
 * All paths are rooted under /api/employees
 */
@RestController
@RequestMapping("/employees")
public class EmployeeService {

    /**
     * Data Access Object (DAO) used to access the business logic layer.
     */
    private final EmployeeDAO employeeDAO;

    /**
     * default constructor, initializes the employee service object for use
     */
    public EmployeeService(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    /**
     * Endpoint #1: Get all departments
     * Retrieves a list of all Department records available
     * Usage (GET): http://localhost:8090/M7_P2_war_exploded/api/employees/getAllDepartments
     *
     * @return A Response containing a JSON list of Department objects
     */
    @GetMapping("/getAllDepartments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        // call service to retrieve a list of all departments
        List<Department> list = employeeDAO.findAllDepartments();
        return ResponseEntity.ok(list);
    }

    /**
     * Endpoint #2: Retrieves specified full Employee record
     *
     * Usage (GET): http://localhost:8090/M7_P2_war_exploded/api/employees/getEmployeeRecord/?empNo=99999
     *
     * @param empNo The employee number to be retrieved, supplied as a Query Parameter.
     * @return Returns Employee JSON object or a string failure message if the employee does not exist
     */
    @GetMapping("/getEmployeeRecord")
    public ResponseEntity<?> getEmployeeRecord(@RequestParam("empNo") int empNo) {
        // Retrieve employee record
        Employee emp = employeeDAO.getEmployeeRecords(empNo);
        if (emp == null) {
            // if employee not found, catch exception and give Response
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested Employee Record not found");
        }

        // return the requested Employee if all ok
        return ResponseEntity.ok(emp);
    }

    /**
     * Endpoint #3: Get paginated EmployeeDTO records by department
     * Retrieves paginated list of EmployeeDTO records belonging to some department,
     * where the requested department is supplied as an argument and each page is
     * limited to 20 entries.
     * If department does not exist, page number invalid, or current page index has no employees,
     * exceptions are caught and handled.
     *
     * Usages (GET): (1) defaulted to page 1 (2) specifying page number
     * (1) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003
     * (2) http://localhost:8090/M7_P2_war_exploded/api/employees/getAllEmployeeRecords/?departmentNo=d003&page=10
     *
     * @param departmentNo name of the department we wish to retrieve employees from
     * @param page         1-indexed page number of the list we want. optional and defaults to 1
     * @return JSON list of EmployeeRecordDTO if success or some HTTP errors upon validation failure
     */
    @GetMapping("/getAllEmployeeRecords")
    public ResponseEntity<?> getAllEmployeeRecords(
            @RequestParam("departmentNo") String departmentNo,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        // CHECK: page number has to be greater than or equal to 1
        if (page < 1) {
            return ResponseEntity
                    .badRequest()
                    .body("Page number must be greater than or equal to 1!");
        }

        try {
            // retrieve the list of employee records
            List<EmployeeRecordDTO> empRecords =
                    employeeDAO.getAllEmployeeRecordsList(departmentNo, page);

            // CHECK: if retrieved page is empty we return appropriate message
            if (empRecords.isEmpty()) {
                return ResponseEntity.ok("Page index contains no employee records!");
            }

            return ResponseEntity.ok(empRecords);

        } catch (InvalidDataException e) {
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body(e.getMessage());
        }
    }

    /**
     * Endpoint #4: Promote Employee
     * Processes a request to update an employee's salary/department/title in a single transaction.
     *
     * Usage (POST): http://localhost:8090/M7_P2_war_exploded/api/employees/promote
     * Input JSON format:
     * {
     *     "empNo":11004,
     *     "newDeptNo":"d007",
     *     "newSalary": 65349,
     *     "newTitle": "Senior Engineer",
     *     "promotionDate": "2024-12-17"
     * }
     *
     * @param request EmployeePromotionRequest JSON payload containing the employee ID and new details.
     * @return Returns HTTP 201 Created on success, HTTP 400/404 on validation/data error,
     * or HTTP 500 on unexpected internal errors.
     */
    @PostMapping("/promote")
    public ResponseEntity<?> promoteEmployee(
            @RequestBody EmployeePromotionRequest request) {

        // try to process promotion request, throw & catch errors if unsuccessful
        try {
            // call helper method to validate, throws exception if invalid
            Helper.validatePromotionRequest(request);

            // call your service to promote the employee
            employeeDAO.promoteEmployee(request);

            // return success response
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Employee promoted successfully");
        }
        catch (DateTimeParseException e) {
            // catch date format errors
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Date must be in YYYY-MM-DD format.");
        }
        catch (InvalidDataException e) {
            // catch exceptions we throw if promotion fails, return a response
            return ResponseEntity
                    .status(e.getStatusCode())
                    .body("Promotion failed: " + e.getMessage());

        } catch (Exception e) {
            // troubleshoot more unexpected errors
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}
