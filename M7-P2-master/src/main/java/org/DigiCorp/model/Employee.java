package org.DigiCorp.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class represents employee table in database, storing employee attributes
 * and providing one-to-many mapping to salaries, titles, department and manager assignments.
 */
@Entity
// named query to retrieve paginated list of employees in some given department
@NamedQuery(name = "Employee.getDepartmentEmployeeRecords",
        query = "SELECT new org.DigiCorp.dto.EmployeeRecordDTO(e.empNo, e.hireDate, e.firstName, e.lastName) " +
                "FROM DeptEmp de JOIN de.employee e " +
                "WHERE de.deptNo = :deptNo ORDER BY e.empNo")
@Table(name = "employees")
public class Employee {
    /**
     * employee number to uniquely identify an employee
     */
    @Id
    @Column(name = "emp_no")
    private int empNo;

    /**
     * employee's date of birth
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * employee's first name
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * employee's last name
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * employee's gender
     */
    @Column(name = "gender")
    private String gender;

    /**
     * employee's hire date
     */
    @Column(name = "hire_date")
    private LocalDate hireDate;


    // --- Mapping ---
    /**
     * list of employee's salary records
     * maps one-to-many relationship to Salary entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("toDate ASC, fromDate ASC")
    private List<Salary> salaryList;

    /**
     * list of employee's title records
     * maps one-to-many relationship to Title entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("toDate ASC, fromDate ASC")
    private List<Title> titleList;

    /**
     * list of employee's department assignment records
     * maps one-to-many relationship to DeptEmp entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("toDate ASC, fromDate ASC")
    private List<DeptEmp> deptEmpList;

    /**
     * list of employee's manager assignment records
     * maps one-to-many relationship to DeptManager entity
     */
    @OneToMany(mappedBy = "employee", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("toDate ASC, fromDate ASC")
    private List<DeptManager> deptManagerList;


    // --- Constructors ---

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public Employee() {
    }

    /**
     * parameterized constructor for creation of Employee instance
     *
     * @param empNo     employee unique ID int
     * @param birthDate employee's date of birth
     * @param firstName employee's first name
     * @param lastName  employee's last name
     * @param gender    employee's gender
     * @param hireDate  employee's hiring date
     */
    public Employee(int empNo, LocalDate birthDate, String firstName,
                    String lastName, String gender, LocalDate hireDate) {
        this.empNo = empNo;
        this.birthDate = birthDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.hireDate = hireDate;
    }

    // --- Getters and Setters ---

    /**
     * retrieves employee number
     *
     * @return the employee number
     */
    public int getEmpNo() {
        return empNo;
    }

    /**
     * sets the employee number
     *
     * @param empNo the new employee number
     */
    public void setEmpNo(int empNo) {
        this.empNo = empNo;
    }

    /**
     * retrieves the employee's date of birth
     *
     * @return the employee's date of birth
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * sets the employee's date of birth
     *
     * @param birthDate the employee's new date of birth
     */
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * retrieves the employee's first name
     *
     * @return the employee's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * sets the employee's first name
     *
     * @param firstName the employee's new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * retrieves the employee's last name
     *
     * @return the employee's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * sets the employee's last name
     *
     * @param lastName the employee's new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * retrieves the employee's gender
     *
     * @return the employee's gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * sets the employee's gender
     *
     * @param gender The employee's new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * retrieves the employee's hire date
     *
     * @return the employee's hire date
     */
    public LocalDate getHireDate() {
        return hireDate;
    }

    /**
     * sets the employee's hire date
     *
     * @param hireDate the employee's new hire date
     */
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    /**
     * retrieves list of historical salary records
     *
     * @return list of historical salary records
     */
    public List<Salary> getSalaryList() {
        return salaryList;
    }

    /**
     * sets the list of historical salary records
     *
     * @param salaryList the new list of historical salary records
     */
    public void setSalaryList(List<Salary> salaryList) {
        this.salaryList = salaryList;
    }

    /**
     * retrieves the list of historical title records
     *
     * @return list of historical title records
     */
    public List<Title> getTitleList() {
        return titleList;
    }

    /**
     * sets the list of historical title records
     *
     * @param titleList new list of historical title records
     */
    public void setTitleList(List<Title> titleList) {
        this.titleList = titleList;
    }

    /**
     * retrieves the list of historical department assignments
     *
     * @return list of historical department assignments
     */
    public List<DeptEmp> getDeptEmpList() {
        return deptEmpList;
    }

    /**
     * sets the list of historical department assignments
     *
     * @param deptEmpList the new list of historical department assignments
     */
    public void setDeptEmpList(List<DeptEmp> deptEmpList) {
        this.deptEmpList = deptEmpList;
    }

    /**
     * retrieves the list of historical department manager assignments
     *
     * @return list of historical department manager assignments
     */
    public List<DeptManager> getDeptManagerList() {
        return deptManagerList;
    }

    /**
     * sets the list of historical department manager assignments
     *
     * @param deptManagerList the new list of historical department manager assignments
     */
    public void setDeptManagerList(List<DeptManager> deptManagerList) {
        this.deptManagerList = deptManagerList;
    }

    /**
     * provides string representation of Employee object
     *
     * @return formatted String of Employee and its attributes
     */
    @Override
    public String toString() {
        return "Employee{" +
                "empNo=" + empNo +
                ", birthDate=" + birthDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", hireDate=" + hireDate +
                '}';
    }
}
