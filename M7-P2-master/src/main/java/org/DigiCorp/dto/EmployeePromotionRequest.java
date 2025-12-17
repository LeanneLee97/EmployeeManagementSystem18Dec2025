package org.DigiCorp.dto;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) representing the request payload for promoting an employee.
 */
public class EmployeePromotionRequest {
    private Integer empNo;
    private String newTitle;
    private Integer newSalary;
    private String newDeptNo;
    private LocalDate promotionDate;

    /**
     * Default no-argument constructor
     */
    public EmployeePromotionRequest() {}

    /**
     * Constructs a new EmployeePromotionRequest.
     *
     * @param empNo The ID of the employee to be promoted.
     * @param newTitle The new job title for the employee.
     * @param newSalary The new salary amount for the employee.
     * @param newDepartment The unique department ID (e.g., 'd005') for the employee's new assignment.
     */
    public EmployeePromotionRequest(Integer empNo, String newTitle, Integer newSalary, String newDepartment) {
        this.empNo = empNo;
        this.newTitle = newTitle;
        this.newSalary = newSalary;
        this.newDeptNo = newDepartment;
    }
    /**
     * Gets the ID of the employee to be promoted.
     *
     * @return The employee number.
     */
    public Integer getEmpNo() {
        return empNo;
    }
    /**
     * Sets the ID of the employee to be promoted.
     *
     * @param empNo The employee number.
     */
    public void setEmpNo(Integer empNo) {
        this.empNo = empNo;
    }
    /**
     * Gets the new job title.
     *
     * @return The new title.
     */
    public String getNewTitle() {
        return newTitle;
    }
    /**
     * Sets the new job title.
     *
     * @param newTitle The new title.
     */
    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }
    /**
     * Gets the new salary amount.
     *
     * @return The new salary.
     */
    public Integer getNewSalary() {
        return newSalary;
    }
    /**
     * Sets the new salary amount.
     *
     * @param newSalary The new salary.
     */
    public void setNewSalary(Integer newSalary) {
        this.newSalary = newSalary;
    }
    /**
     * Gets the new department ID.
     *
     * @return The unique department ID (e.g., 'd005').
     */
    public String getNewDeptNo() {
        return newDeptNo;
    }
    /**
     * Sets the new department ID.
     *
     * @param newDeptNo The unique department ID.
     */
    public void setNewDeptNo(String newDeptNo) {
        this.newDeptNo = newDeptNo;
    }

    /**
     * Gets the promotion effective date.
     *
     * @return The promotion date, or null if not specified (defaults to today).
     */
    public LocalDate getPromotionDate() {
        return promotionDate;
    }

    /**
     * Sets the promotion effective date.
     *
     * @param promotionDate The date when the promotion takes effect. If null, defaults to today.
     */
    public void setPromotionDate(LocalDate promotionDate) {
        this.promotionDate = promotionDate;
    }
}
