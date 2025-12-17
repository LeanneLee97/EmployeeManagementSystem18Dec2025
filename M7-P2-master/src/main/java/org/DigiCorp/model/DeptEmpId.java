package org.DigiCorp.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite Primary Key for DeptEmp entity, combining the
 * employee number and department number
 *
 * Serializable is implemented as required for @IdClass usage
 */
public class DeptEmpId implements Serializable {
    /**
     * employee number that forms part of the primary composite key
     */
    private int employee;
    /**
     * department number that forms part of the primary composite key
     */
    private String deptNo;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public DeptEmpId() {
    }

    /**
     * parameterized constructor for explicit key creation
     *
     * @param employee  the employee's id number
     * @param deptNo the department's id string
     */
    public DeptEmpId(int employee, String deptNo) {
        this.employee = employee;
        this.deptNo = deptNo;
    }

    // getters and setters

    /**
     * retrieves the employee number
     *
     * @return the employee number
     */
    public int getEmpNo() {
        return employee;
    }

    /**
     * sets the employee number
     *
     * @param employee the new employee number
     */
    public void setEmpNo(int employee) {
        this.employee = employee;
    }

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
        if (!(o instanceof DeptEmpId deptEmpId)) return false;
        return employee == deptEmpId.employee && Objects.equals(deptNo, deptEmpId.deptNo);
    }
    /**
     * Returns a hash code
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(employee, deptNo);
    }
}
