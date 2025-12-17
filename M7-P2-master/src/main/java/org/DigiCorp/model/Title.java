package org.DigiCorp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * class represents titles table, recording history of job titles of
 * an employee.
 * Maps one-to-many relationship with Employee entity.
 * Uses composite primary key TitleId composed of employee, title, fromDate
 */
@Entity
@Table(name = "titles")
// specifies class to represent composite primary key
@IdClass(TitleId.class)
public class Title {

    // mapping
    /**
     * maps many-to-one relationship with Employee entity
     * forms foreign key link via emp_no
     * JsonIgnore prevents serialization
     */
    @Id
    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    @JsonIgnore
    private Employee employee;

    /**
     * employee's job title, part of composite primary key
     */
    @Id
    @Column(name = "title")
    private String title;

    /**
     * employee's starting date of this title, part of composite primary key
     */
    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    /**
     * employee's ending date of this title
     */
    @Column(name = "to_date")
    private LocalDate toDate;

    // constructors

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public Title() {
    }

    /**
     * parameterized constructor to create Title instance
     *
     * @param employee Employee object
     * @param title    job title
     * @param fromDate starting date for the title
     * @param toDate   ending date for the title
     */
    public Title(Employee employee, String title, LocalDate fromDate, LocalDate toDate) {
        this.employee = employee;
        this.title = title;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    // getters and setters

    /**
     * retrieves associated Employee entity
     *
     * @return Employee object
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * sets associated Employee entity
     *
     * @param employee Employee object
     */
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    /**
     * retrieves job title
     *
     * @return job title string
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets job title
     *
     * @param title job title string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * retrieves starting date of title
     *
     * @return starting date, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets starting date of title
     *
     * @param fromDate starting date of title
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * retrieves ending date of title
     *
     * @return ending date, toDate
     */
    public LocalDate getToDate() {
        return toDate;
    }

    /**
     * sets ending date of title
     *
     * @param toDate ending date of title
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    /**
     * provides string representation of Title
     *
     * @return formatted string containing title attributes
     */
    @Override
    public String toString() {
        return "Title{" +
                "employee=" + employee +
                ", title='" + title + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}
