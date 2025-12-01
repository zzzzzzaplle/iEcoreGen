import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** List of departments belonging to the company. */
    private List<Department> departments = new ArrayList<>();

    /** List of employees belonging to the company. */
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
        // default constructor
    }

    /* ---------- Getters & Setters ---------- */

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /* ---------- Business Methods ---------- */

    /**
     * Adds a department to the company if it is not already present.
     *
     * @param department the department to add
     * @return {@code true} if the department was added, {@code false} otherwise
     */
    public boolean addDepartment(Department department) {
        if (department == null) {
            return false;
        }
        if (departments.contains(department)) {
            return false;
        }
        return departments.add(department);
    }

    /**
     * Adds an employee to the company if it is not already present.
     *
     * @param employee the employee to add
     * @return {@code true} if the employee was added, {@code false} otherwise
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Deletes a department from the company together with its offices and, if present,
     * its manager from the employee list.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed, {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove the department from the company's list
        departments.remove(department);

        // Remove the manager (if any) from the company's employee list
        Employee manager = department.getManager();
        if (manager != null) {
            employees.remove(manager);
        }

        // Offices are owned only by the department; after removal they become eligible for GC.
        // No further action required.

        return true;
    }

    /**
     * Returns a list of departments that do not have a manager assigned.
     * If every department already has a manager, {@code null} is returned.
     *
     * @return an unmodifiable list of departments without a manager, or {@code null}
     *         if all departments have managers
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> withoutManager = new ArrayList<>();
        for (Department d : departments) {
            if (!d.hasManager()) {
                withoutManager.add(d);
            }
        }
        return withoutManager.isEmpty() ? null : Collections.unmodifiableList(withoutManager);
    }

    /**
     * Assigns an employee as the manager of a given department.
     * Both the employee and the department must already belong to the company,
     * and the department must not already have a manager.
     *
     * @param employee   the employee to become manager
     * @param department the department that will receive the manager
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        if (department.getManager() != null) {
            return false; // department already has a manager
        }
        department.setManager(employee);
        return true;
    }

    /**
     * Checks whether a particular department has a manager assigned.
     *
     * @param department the department to check
     * @return {@code true} if the department has a manager, {@code false} otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.hasManager();
    }
}

/**
 * Represents a department within a company.
 */
class Department {

    /** Offices belonging to this department. */
    private List<Office> offices = new ArrayList<>();

    /** The headquarter office of this department (may be {@code null}). */
    private Office headquarter;

    /** The manager of this department (may be {@code null}). */
    private Employee manager;

    /** No‑argument constructor. */
    public Department() {
        // default constructor
    }

    /* ---------- Getters & Setters ---------- */

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public Office getHeadquarter() {
        return headquarter;
    }

    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /* ---------- Business Methods ---------- */

    /**
     * Assigns an existing office of this department as the headquarter.
     * The headquarter can be set only once and the office must belong to the department.
     *
     * @param office the office to become the headquarter
     * @return {@code true} if the office was successfully assigned as headquarter,
     *         {@code false} if the office does not belong to the department or a headquarter
     *         is already set
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        if (!offices.contains(office)) {
            return false; // office not part of this department
        }
        if (headquarter != null) {
            return false; // headquarter already assigned
        }
        headquarter = office;
        return true;
    }

    /**
     * Checks whether this department currently has a manager assigned.
     *
     * @return {@code true} if a manager is assigned, {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from this department. The only employee that can be
     * directly removed is the manager; if the given employee is the manager,
     * the manager reference is cleared.
     *
     * @param employee the employee to delete
     * @return {@code true} if the employee was the manager and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (Objects.equals(manager, employee)) {
            manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from this department. If the office is currently the headquarter,
     * the headquarter reference is cleared as well.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed in the department and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }
        boolean removed = offices.remove(office);
        if (removed && Objects.equals(headquarter, office)) {
            headquarter = null;
        }
        return removed;
    }

    /**
     * Adds an office to the department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added, {@code false} if it was {@code null}
     *         or already present
     */
    public boolean addOffice(Office office) {
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }
}

/**
 * Represents an office location.
 */
class Office {

    /** Simple identifier for the office (e.g., address or code). */
    private String identifier;

    /** No‑argument constructor. */
    public Office() {
        // default constructor
    }

    /* ---------- Getters & Setters ---------- */

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /* ---------- Equality helpers (optional) ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;
        return Objects.equals(identifier, office.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}

/**
 * Represents an employee.
 */
class Employee {

    /** Simple identifier for the employee (e.g., employee number). */
    private String employeeId;

    /** Employee's full name. */
    private String name;

    /** No‑argument constructor. */
    public Employee() {
        // default constructor
    }

    /* ---------- Getters & Setters ---------- */

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   /* ---------- Equality helpers (optional) ---------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }
}