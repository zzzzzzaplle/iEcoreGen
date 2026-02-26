import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** List of departments belonging to the company. */
    private List<Department> departments;

    /** List of employees belonging to the company. */
    private List<Employee> employees;

    /** No‑argument constructor that initializes internal collections. */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees    = new ArrayList<>();
    }

    /* ==================== Getters & Setters ==================== */

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

    /* ==================== Business Operations ==================== */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return {@code true} if the department was added, {@code false} if it already exists
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
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     * @return {@code true} if the employee was added, {@code false} if it already exists
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
     * Assigns an employee as the manager of a given department.
     * The employee and department must both belong to this company.
     *
     * @param employee   the employee to become manager
     * @param department the department to which the manager is assigned
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        // verify that both belong to this company
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        // department already has a manager?
        if (department.getManager() != null) {
            return false;
        }
        department.setManager(employee);
        return true;
    }

    /**
     * Deletes a department from the company. The department, its offices (including
     * the headquarter) and its manager (if any) are removed from the company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed, {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null) {
            return false;
        }
        if (!departments.contains(department)) {
            return false;
        }

        // Remove manager from company employee list, if present
        Employee manager = department.getManager();
        if (manager != null) {
            employees.remove(manager);
        }

        // No explicit storage of offices in Company, they disappear with the department
        departments.remove(department);
        return true;
    }

    /**
     * Returns a list of departments that do not have a manager assigned.
     * If every department already has a manager, {@code null} is returned.
     *
     * @return a list of departments without a manager, or {@code null} if none
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department d : departments) {
            if (!d.hasManager()) {
                result.add(d);
            }
        }
        return result.isEmpty() ? null : result;
    }
}

/**
 * Represents a department within a company.
 */
class Department {

    /** Offices belonging to this department. */
    private List<Office> offices;

    /** The headquarter office of the department (may be {@code null}). */
    private Office headquarter;

    /** The manager employee of the department (may be {@code null}). */
    private Employee manager;

    /** No‑argument constructor that initializes internal collections. */
    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }

    /* ==================== Getters & Setters ==================== */

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

    /* ==================== Helper Methods ==================== */

    /**
     * Adds an office to the department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added, {@code false} otherwise
     */
    public boolean addOffice(Office office) {
        if (office == null) {
            return false;
        }
        if (offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

    /**
     * Assigns the given office as the headquarter of the department.
     * The office must already belong to the department and the department
     * must not already have a headquarter.
     *
     * @param office the office to become headquarter
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        // office must belong to this department
        if (!offices.contains(office)) {
            return false;
        }
        // department must not already have a headquarter
        if (this.headquarter != null) {
            return false;
        }
        this.headquarter = office;
        return true;
    }

    /**
     * Checks whether this department currently has a manager assigned.
     *
     * @return {@code true} if a manager is assigned, {@code false} otherwise
     */
    public boolean hasManager() {
        return this.manager != null;
    }

    /**
     * Deletes an employee from the department. In this model the only
     * employee directly linked to a department is the manager, so the method
     * removes the manager if the supplied employee matches.
     *
     * @param employee the employee to delete
     * @return {@code true} if the employee was the manager and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employee.equals(this.manager)) {
            this.manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from the department. If the office being removed is
     * currently the headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed and was removed, {@code false} otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }
        boolean removed = offices.remove(office);
        if (removed && office.equals(this.headquarter)) {
            this.headquarter = null;
        }
        return removed;
    }
}

/**
 * Represents an office location.
 */
class Office {

    /** Simple identifier for the office (could be address, name, etc.). */
    private String identifier;

    /** No‑argument constructor. */
    public Office() {
        // default identifier is empty
        this.identifier = "";
    }

    /* ==================== Getters & Setters ==================== */

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /* ==================== Equality ==================== */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Office)) return false;
        Office other = (Office) obj;
        return (identifier == null ? other.identifier == null : identifier.equals(other.identifier));
    }

    @Override
    public int hashCode() {
        return identifier == null ? 0 : identifier.hashCode();
    }
}

/**
 * Represents an employee of the company.
 */
class Employee {

    /** Simple identifier for the employee (e.g., employee number). */
    private String employeeId;

    /** Human‑readable name of the employee. */
    private String name;

    /** No‑argument constructor. */
    public Employee() {
        this.employeeId = "";
        this.name = "";
    }

    /* ==================== Getters & Setters ==================== */

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

   /* ==================== Equality ==================== */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Employee)) return false;
        Employee other = (Employee) obj;
        return (employeeId == null ? other.employeeId == null : employeeId.equals(other.employeeId));
    }

    @Override
    public int hashCode() {
        return employeeId == null ? 0 : employeeId.hashCode();
    }
}