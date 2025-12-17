package org.DigiCorp.util;

import org.DigiCorp.dto.EmployeePromotionRequest;
import org.DigiCorp.exceptions.InvalidDataException;

import java.time.LocalDate;

/**
 * Utility class providing helper methods for common operations, such as
 * string manipulation and API request validation.
 */
public class Helper {


    /**
     * Converts the input string into Title Case
     *
     * @param inputString The string to be converted.
     * @return The string in Title Case format, with leading/trailing whitespace removed.
     */
    public static String toTitleCase(String inputString) {
        StringBuilder tempTitle = new StringBuilder();
        for (String word : inputString.split("\\s+")) {
            if (!word.isEmpty()) {
                tempTitle.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return tempTitle.toString().trim();
    }


    /**
     * Performs input validation on EmployeePromotionRequest payload.
     * Checks include ensuring these four fields (empNo, newTitle, newDeptNo, newSalary) are present (not null),
     * the salary is positive, and the title length is within acceptable bounds (1 to 50 characters).
     * <p>
     * This is input validation (Controller layer) - checks if data is well-formed.
     *
     * @param request EmployeePromotionRequest payload received from the client.
     * @throws InvalidDataException If any input validation constraint is violated (e.g., missing data, non-positive salary).
     */
    public static void validatePromotionRequest(EmployeePromotionRequest request) throws InvalidDataException {
        // CHECK: ALL fields must be provided (promotionDate is optional)
        if (request.getEmpNo() == null ||
                request.getNewTitle() == null ||
                request.getNewDeptNo() == null ||
                request.getNewSalary() == null) {
            throw new InvalidDataException("Please provide all 4: empNo, newSalary, newTitle, newDeptNo", 400);
        }
        // CHECK: salary value is positive
        if (request.getNewSalary() < 1) {
            throw new InvalidDataException("Salary must be positive", 400);
        }

        // CHECK: input title more than 0 less than 51
        if (request.getNewTitle().isEmpty() || request.getNewTitle().length() > 50) {
            throw new InvalidDataException("New Title length invalid", 400);
        }
    }

    /**
     * Performs business validation on promotion date.
     * Checks if the promotion date (if provided) is not earlier than the employee's earliest salary start date.
     * <p>
     * This is business validation (Service layer) - checks if data makes sense for business rules.
     *
     * @param promotionDate The promotion date to validate, or null if not provided.
     * @param earliestSalaryDate The earliest from_date from the employee's salary records.
     * @throws InvalidDataException If promotion date violates business rules (e.g., earlier than employee's start date).
     */
    public static void validatePromotionDate(LocalDate promotionDate, LocalDate earliestSalaryDate) throws InvalidDataException {
        // CHECK: Promotion date (if provided) must not be earlier than employee's earliest salary start date
        if (promotionDate != null && earliestSalaryDate != null) {
            if (promotionDate.isBefore(earliestSalaryDate)) {
                throw new InvalidDataException(
                        "Promotion date cannot be earlier than employee's start date: " + earliestSalaryDate, 400);
            }
        }
    }
}
