package org.DigiCorp.dto;


import java.time.LocalDate;

/**
 * Employee Record DTO (Data Transfer Object) used specifically for
 * transferring a streamlined set of employee data from the server back to the client,
 * avoiding the transfer of unnecessary data.
 */
public class EmployeeRecordDTO {
    /**
     * The employee's employee number.
     */
    private int empNo;
    /**
     * The date the employee was hired.
     */
    private LocalDate hireDate;
    /**
     * The employee's first name.
     */
    private String firstName;
    /**
     * The employee's last name.
     */
    private String lastName;

    // constructors

    /**
     * Default public constructor as required by JPA/Hibernate
     */
    public EmployeeRecordDTO() {
    }

    /**
     * Parameterized constructor to initialize a DTO instance
     *
     * @param empNo     employee's unique ID number
     * @param hireDate  date employee was hired
     * @param firstName employee's first name
     * @param lastName  employee's last name
     */
    public EmployeeRecordDTO(int empNo, LocalDate hireDate, String firstName, String lastName) {
        this.empNo = empNo;
        this.hireDate = hireDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // getters/setters

    /**
     * Retrieves the employee's unique ID number.
     *
     * @return The employee number as an integer.
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * Sets the employee's unique ID number.
     *
     * @param empNo The new employee number.
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * Retrieves the employee's hire date
     *
     * @return The hire date of the employee
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * Sets the employee's hire date.
     *
     * @param hireDate The new LocalDate hire date
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * Retrieves the employee's first name.
     *
     * @return The first name as a String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the employee's first name.
     *
     * @param firstName The new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the employee's last name.
     *
     * @return The last name as a String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the employee's last name.
     *
     * @param lastName The new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}




