package org.DigiCorp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.DigiCorp.dto.*;
import org.DigiCorp.exceptions.InvalidDataException;
import org.DigiCorp.util.Helper;
import org.DigiCorp.model.*;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Provides business logic for managing Employee data.
 * This class acts as a Data Access Object (DAO), managing transactions and
 * executing JPA queries to interact with the database.
 */
@Repository
public class EmployeeDAO {

    /**
     * EntityManager injected by Spring (replaces JPAUtil)
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Endpoint #1
     * Retrieves a list of all Department entities from the database by
     * executing a named query
     *
     * @return List of all Department entities.
     */
    public List<Department> findAllDepartments() {
        // retrieves all departments using the named query
        TypedQuery<Department> query =
                em.createNamedQuery("Department.findAllDepartments", Department.class);
        return query.getResultList();
    }

    /**
     * Endpoint #2:
     * Retrieves the complete record for a specific employee.
     * <p>
     * This method fetches full record Employee entity
     *
     * @param empNo The primary key Employee entity.
     * @return The Employee entity corresponding to the supplied key, or null if not found.
     */
    public Employee getEmployeeRecords(int empNo) {
        // find employee via primary key
        return em.find(Employee.class, empNo);
    }

    /**
     * Endpoint #3
     * Retrieves a paginated list of employee records for a specific department
     * by executing a named query
     *
     * @param deptNo The department number (e.g., 'd005') used to filter the employees.
     * @param page   The requested page number (1-indexed). Results are capped at 20 per page.
     * @return A paginated List of EmployeeRecordDTO objects.
     * @throws InvalidDataException If the supplied deptNo does not correspond to an existing Department.
     */
    public List<EmployeeRecordDTO> getAllEmployeeRecordsList(String deptNo, int page)
            throws InvalidDataException {

        // CHECK: if dept supplied, does it belong in the department list?
        if (em.find(Department.class, deptNo) == null) {
            throw new InvalidDataException("Department " + deptNo + " does not exist.", 404);
        }

        // execute named query to retrieve List of EmployeeDTO records
        // we supply deptNo as a key, convert the page to 0-index, cap the results to 20
        return em.createNamedQuery(
                        "Employee.getDepartmentEmployeeRecords",
                        EmployeeRecordDTO.class)
                .setParameter("deptNo", deptNo)
                .setFirstResult((page - 1) * 20)
                .setMaxResults(20)
                .getResultList();
    }

    /**
     * Promotes an employee by updating their salary/department/title in a single transaction.
     * <p>
     * This method closes existing records by setting toDate = promotionDate (or today if not specified)
     * and inserts new records with fromDate = promotionDate and toDate = '9999-01-01'
     *
     * @param request EmployeePromotionRequest payload
     * @throws InvalidDataException when validation fails
     */
    @Transactional
    public void promoteEmployee(EmployeePromotionRequest request)
            throws InvalidDataException {

        // CHECK: Employee must exist
        Employee emp = em.find(Employee.class, request.getEmpNo());
        if (emp == null) {
            throw new InvalidDataException("Employee does not exist", 404);
        }

        // Business validation (Service layer) - check if promotion date makes sense for business rules
        LocalDate latestSalaryDate = emp.getSalaryList().stream()
                .map(Salary::getFromDate)
                .max(LocalDate::compareTo)
                .orElse(null);

        Helper.validatePromotionDate(request.getPromotionDate(), latestSalaryDate);

        Title currentTitle = emp.getTitleList().getLast();
        Salary currentSalary = emp.getSalaryList().getLast();
        DeptEmp currentDeptEmp = emp.getDeptEmpList().getLast();

        DeptManager currentDeptManager =
                emp.getDeptManagerList().isEmpty() ? null : emp.getDeptManagerList().getLast();

        // CHECK: Employee must be current
        if (!currentSalary.getToDate().equals(LocalDate.of(9999, 1, 1))) {
            throw new InvalidDataException("Employee is no longer with the company", 400);
        }

        boolean salaryChanged = request.getNewSalary() != currentSalary.getSalary();
        boolean deptChanged = !request.getNewDeptNo().equalsIgnoreCase(currentDeptEmp.getDeptNo());
        boolean titleChanged = !request.getNewTitle().equalsIgnoreCase(currentTitle.getTitle());

        if (!salaryChanged && !deptChanged && !titleChanged) {
            throw new InvalidDataException(
                    "Provided data matches existing data, no changes requested", 400);
        }

        // CHECK: Department exists
        if (deptChanged && em.find(Department.class, request.getNewDeptNo()) == null) {
            throw new InvalidDataException(
                    "Department " + request.getNewDeptNo() + " does not exist.", 404);
        }

        // Use promotionDate if provided, otherwise default to today
        LocalDate effectiveDate = request.getPromotionDate() != null
                ? request.getPromotionDate()
                : LocalDate.now();

        // CHECK: Employee cannot be promoted twice on the same date
        // Check if any record (salary, title, or department) was already created on this date
        boolean alreadyPromotedOnDate = emp.getSalaryList().stream()
                .anyMatch(s -> s.getFromDate().isEqual(effectiveDate)) ||
                emp.getTitleList().stream()
                        .anyMatch(t -> t.getFromDate().isEqual(effectiveDate)) ||
                emp.getDeptEmpList().stream()
                        .anyMatch(d -> d.getFromDate().isEqual(effectiveDate));

        if (alreadyPromotedOnDate) {
            throw new InvalidDataException(
                    "Employee has already been promoted on " + effectiveDate + " and cannot be promoted again on the same date", 400);
        }

        /* ---------- Salary Update ---------- */
        if (salaryChanged) {
            currentSalary.setToDate(effectiveDate);
            em.merge(currentSalary);

            Salary newSalary = new Salary(
                    emp, effectiveDate, LocalDate.of(9999, 1, 1), request.getNewSalary());
            em.persist(newSalary);
        }

        /* ---------- Department Update ---------- */
        if (deptChanged) {
            for (DeptEmp d : emp.getDeptEmpList()) {
                if (d.getDeptNo().equalsIgnoreCase(request.getNewDeptNo())) {
                    throw new InvalidDataException(
                            "Employee cannot return to their previous department", 400);
                }
            }

            currentDeptEmp.setToDate(effectiveDate);
            em.merge(currentDeptEmp);

            DeptEmp newDeptEmp = new DeptEmp(
                    emp, request.getNewDeptNo().toLowerCase(), effectiveDate, LocalDate.of(9999, 1, 1));
            em.persist(newDeptEmp);
        }

        /* ---------- Title Update ---------- */
        if (titleChanged) {
            String inputTitle = Helper.toTitleCase(request.getNewTitle());

            currentTitle.setToDate(effectiveDate);
            em.merge(currentTitle);

            Title newTitle = new Title(
                    emp, inputTitle, effectiveDate, LocalDate.of(9999, 1, 1));
            em.persist(newTitle);

            // Manager -> Non-manager
            if ("Manager".equals(currentTitle.getTitle()) && !"Manager".equals(inputTitle)) {
                if (currentDeptManager != null) {
                    currentDeptManager.setToDate(effectiveDate);
                    em.merge(currentDeptManager);
                }
            }

            // Non-manager -> Manager
            if ("Manager".equals(inputTitle)) {
                DeptManager newManager = new DeptManager(
                        emp, request.getNewDeptNo().toLowerCase(), effectiveDate, LocalDate.of(9999, 1, 1));
                em.persist(newManager);
            }
        }
    }

}