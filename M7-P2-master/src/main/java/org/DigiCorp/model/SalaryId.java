package org.DigiCorp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * class serves as composite primary key for Salary entity
 * combines employee reference and start date to uniquely
 * identify Salary record.
 * implements Serializable as required for @IdClass implementation
 */
public class SalaryId implements Serializable {
    /**
     * int employee id number, part of primary composite key
     */
    private int employee;

    /**
     * employee's start date of salary, part of primary composite key
     */
    private LocalDate fromDate;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public SalaryId() {
    }

    /**
     * parameterized constructor to create new composite key instance
     *
     * @param employee Employee's unique ID int
     * @param fromDate starting date of the salary entry
     */
    public SalaryId(int employee, LocalDate fromDate) {
        this.employee = employee;
        this.fromDate = fromDate;
    }

    // getters and setters

    /**
     * retrieves the employee ID
     *
     * @return the employee ID
     */
    public int getEmployee() {
        return employee;
    }

    /**
     * sets the employee ID
     *
     * @param employee the new employee ID
     */
    public void setEmployee(int employee) {
        this.employee = employee;
    }

    /**
     * retrieves the starting date, fromDate
     *
     * @return the starting date, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the starting date, fromDate
     *
     * @param fromDate the new starting date, fromDate
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    // equals and hashcode
    /**
     * Performs comparison to specified object.
     * boolean true if exact match and false otherwise
     *
     * @param o The object to be compared against
     * @return true if the objects are the same; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SalaryId salaryId)) return false;
        return employee == salaryId.employee && Objects.equals(fromDate, salaryId.fromDate);
    }
    /**
     * Returns a hash code
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(employee, fromDate);
    }
}
