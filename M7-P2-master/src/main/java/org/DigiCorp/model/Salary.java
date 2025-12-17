package org.DigiCorp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * class represents salaries tables in database, stores salary
 * records for every employee and the dates they were active
 */
@Entity
@Table(name = "salaries")
// using SalaryId class to represent composite primary key
@IdClass(SalaryId.class)
public class Salary {

    /**
     * maps many-to-one relationship to Employee entity and serves as part of composite primary key.
     * JsonIgnore prevents JSON serialization
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    private Employee employee;

    /**
     * start date of specified salary record, part of composite primary key
     */
    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * end date of specified salary record,
     * normally set as '9999-01-01' to indicate ongoing entry
     */
    @Column(name = "to_date")
    private LocalDate toDate;

    /**
     * annual salary amount for the specified salary record
     */
    @Column(name = "salary")
    private int salary;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public Salary() {
    }

    /**
     * parameterized constructor to create a Salary instance
     *
     * @param employee Employee entity
     * @param fromDate start date of salary record
     * @param toDate   end date of salary record
     * @param salary   integer value of annual salary record
     */
    public Salary(Employee employee, LocalDate fromDate, LocalDate toDate, int salary) {
        this.employee = employee;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.salary = salary;
    }

    // getters and setters

    /**
     * retrieves the Employee entity associated with the salary record
     *
     * @return the associated Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * sets the Employee entity associated with the salary record
     *
     * @param employee the new associated Employee object
     */
    public void setEmployee(Employee employee) {
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

    /**
     * retrieves the ending date, toDate
     *
     * @return the new ending date, toDate
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * sets the ending date, toDate
     *
     * @param toDate the new ending date, toDate
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    /**
     * retrieves the annual salary amount
     *
     * @return the annual salary amount
     */
    public int getSalary() {
        return salary;
    }

    /**
     * sets the annual salary amount
     *
     * @param salary the new annual salary amount
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Provides string representation of Salary object
     *
     * @return formatted string with the salary attributes
     */
    @Override
    public String toString() {
        return "Salary{" +
                "employee=" + employee +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", salary=" + salary +
                '}';
    }
}
