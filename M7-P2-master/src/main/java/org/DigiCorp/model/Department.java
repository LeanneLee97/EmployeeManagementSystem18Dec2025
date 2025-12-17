package org.DigiCorp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

/**
 * Department represents the departments table in the database.
 * This entity contains department information and mappings to DeptEmp and DeptManager.
 */
@Entity
// named query to retrieve all department records, used for endpoint #1
@NamedQuery(name = "Department.findAllDepartments", query = "SELECT d FROM Department d")
@Table(name = "departments")
public class Department {
    /**
     * The unique identification number of the department (Primary Key).
     * Maps to the dept_no column.
     */
    @Id
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * The name of the department.
     * Maps to the dept_name column.
     */
    @Column(name = "dept_name")
    private String deptName;

    // mapping
    /**
     * list of employees linked to the department.
     * Maps one-to-many relationship with DeptEmp entity.
     *
     * @JsonIgnore prevents the list from being serialized
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DeptEmp> deptEmpList;

    /**
     * list of department managers linked to the department.
     * Maps one-to-many relationship with DeptManager entity.
     *
     * @JsonIgnore prevents the list from being serialized
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<DeptManager> deptManagerList;


    // constructors

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public Department() {
    }

    // getters/setters

    /**
     * retrieves department number
     *
     * @return department number e.g. d001
     */
    public String getDeptNo() {
        return deptNo;
    }

    /**
     * sets the department number
     *
     * @param deptNo the new department number to be set
     */
    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    /**
     * gets the department name
     *
     * @return name of the department
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * sets the department name
     *
     * @param deptName new name of department
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * retrieves list of employee department records
     *
     * @return list of DeptEmp objects
     */
    public List<DeptEmp> getDeptEmpList() {
        return deptEmpList;
    }

    /**
     * sets the list of employee department records
     *
     * @param deptEmpList the new list of DeptEmp objects
     */
    public void setDeptEmpList(List<DeptEmp> deptEmpList) {
        this.deptEmpList = deptEmpList;
    }

    /**
     * retrieves list of manager department records
     *
     * @return list of DeptManager objects
     */
    public List<DeptManager> getDeptManagerList() {
        return deptManagerList;
    }

    /**
     * sets the list of manager department records
     *
     * @param deptManagerList the new list of deptManager objects
     */
    public void setDeptManagerList(List<DeptManager> deptManagerList) {
        this.deptManagerList = deptManagerList;
    }

    /**
     * provides string representation of the Department object
     *
     * @return formatted string of Department's contents
     */
    @Override
    public String toString() {
        return "Department{" +
                "deptNo='" + deptNo + '\'' +
                ", deptName='" + deptName + '\'' +
                ", deptEmpList=" + deptEmpList +
                ", deptManagerList=" + deptManagerList +
                '}';
    }
}
