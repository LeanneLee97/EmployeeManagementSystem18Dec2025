package org.DigiCorp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * class serves as composite primary key for Title entity,
 * composed of Employee object, job title, starting date of title.
 * implements Serializable as required for @IdClass implementation
 */
public class TitleId implements Serializable {

    /**
     * Employee id, part of composite primary key
     */
    private int employee;

    /**
     * job title, part of primary composite key
     */
    private String title;

    /**
     * starting date of title, part of primary composite key
     */
    private LocalDate fromDate;

    /**
     * default public constructor as required by JPA/Hibernate
     */
    public TitleId() {
    }

    /**
     * parameterized constructor for creating new TitleId instance
     *
     * @param employee Employee id
     * @param title    job title
     * @param fromDate starting date of the job title
     */
    public TitleId(int employee, String title, LocalDate fromDate) {
        this.employee = employee;
        this.title = title;
        this.fromDate = fromDate;
    }

    // getters and setters

    /**
     * retrieve the Employee id
     *
     * @return the Employee id
     */
    public int getEmployee() {
        return employee;
    }

    /**
     * sets the Employee id
     *
     * @param employee the new Employee no
     */
    public void setEmployee(int employee) {
        this.employee = employee;
    }

    /**
     * retrieves the job title
     *
     * @return the job title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the job title
     *
     * @param title the new job title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * retrieves the starting date of job title, fromDate
     *
     * @return the starting date of job title, fromDate
     */
    public LocalDate getFromDate() {
        return fromDate;
    }

    /**
     * sets the starting date of job title, fromDate
     *
     * @param fromDate the new starting date of job title, fromDate
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Performs comparison to specified object.
     * boolean true if exact match and false otherwise
     *
     * @param o The object to be compared against
     * @return true if the objects are the same; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TitleId titleId)) return false;
        return employee == titleId.employee && Objects.equals(title, titleId.title) && Objects.equals(fromDate, titleId.fromDate);
    }
    /**
     * Returns a hash code
     *
     * @return A hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(employee, title, fromDate);
    }
}
