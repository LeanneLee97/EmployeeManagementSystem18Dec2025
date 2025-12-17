package org.DigiCorp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;


/**
 * represents dept_manager table that records the history of
 * employees that have served as a given department's manager.
 * maps many-to-one relations with Employee and Department.
 * it has composite primary key DeptManagerId using empNo and deptNo
 */
@Entity
@Table(name = "dept_manager")
// class to represent composite primary key
@IdClass(DeptManagerId.class)
public class DeptManager {

    // mapping
    /**
     * maps many-to-one relationship to Employee entity,
     * is a foreign key link via emp_no
     * JsonIgnore annotation prevents JSON serialization
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    private Employee employee;

    /**
     * department number that is part of primary composite key
     */
    @Id
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * start date of employee as a department's manager
     */
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * end date of employee as a department's manager
     */
    @Column(name = "to_date")
    private LocalDate toDate;



    /**
     * maps many-to-one relationship to Department entity
     * is a foreign key link to dept_no
     * JsonIgnore annotation prevents JSON serialization
     */
    @ManyToOne
    @JoinColumn(name = "dept_no", referencedColumnName = "dept_no", insertable=false, updatable=false)
    @JsonIgnore
    private Department department;


    // constructors

    /**
     * Default public constructor as required by JPA/Hibernate
     */
    public DeptManager() {
    }

    /**
     * Parameterized constructor for creating DeptManager instance
     *
     * @param employee    employee object
     * @param deptNo   department unique ID string
     * @param fromDate start date of manager at the department
     * @param toDate   end date of manager at the department
     */
    public DeptManager(Employee employee, String deptNo, LocalDate fromDate, LocalDate toDate) {
        this.employee = employee;
        this.deptNo = deptNo;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    // getters and setters

    /**
     * retrieves the department number
     *
     * @return the department number
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * sets the department number
     *
     * @param deptNo the new department number
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * retrieves start date of the manager
     *
     * @return the manager's start date
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the start date of the manager
     *
     * @param fromDate the manager's new start date
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * retrieves the end date of the manager
     *
     * @return the manager's end date
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * sets the end date of the manager
     *
     * @param toDate the manager's new end date
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    /**
     * retrieves the associated Employee entity
     *
     * @return associated Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * sets the associated Employee entity
     *
     * @param employee the new associated Employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * retrieves the associated Department entity
     *
     * @return the associated Department object
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * sets the associated Department entity
     *
     * @param department the new associated Department object
     */
    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * provides string representation of DeptManager and its attributes
     *
     * @return Formatted string of DeptManager and its attributes
     */
    @Override
    public String toString() {
        return "DeptManager{" +
                "employee=" + employee +
                ", deptNo='" + deptNo + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
