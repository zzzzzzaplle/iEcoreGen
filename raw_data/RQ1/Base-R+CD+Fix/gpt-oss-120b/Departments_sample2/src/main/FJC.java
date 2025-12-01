import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** List of all departments belonging to the company. */
    private List<Department> departments;

    /** List of all employees belonging to the company. */
    private List<Employee> employees;

    /** No‑argument constructor required by the task. */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

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

    /* --------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* --------------------------------------------------------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return {@code true} if the department was not already present and was added,
     *         {@code false} otherwise
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
     * @return {@code true} if the employee was not already present and was added,
     *         {@code false} otherwise
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
     * Deletes a department from the company. The method also removes all offices
     * that belong to the department and any employees (including the manager) that
     * are assigned to that department.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove the department itself
        departments.remove(department);

        // Remove all employees that belong to this department
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            Employee emp = it.next();
            if (department.equals(emp.getDepartment())) {
                it.remove();
            }
        }

        // Offices are owned only by the department; after removing the department,
        // they become eligible for garbage collection.
        return true;
    }

    /**
     * Returns a list of departments that have not been assigned a manager.
     *
     * @return a {@link List} of departments without a manager, or {@code null}
     *         if every department already has a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department dept : departments) {
            if (!dept.hasManager()) {
                result.add(dept);
            }
        }
        return result.isEmpty() ? null : result;
    }

    /**
     * Assigns an employee as the manager of a specific department.
     * Both the employee and the department must belong to this company.
     *
     * @param employee   the employee to become manager
     * @param department the department that will receive the manager
     * @return {@code true} if the assignment succeeded, {@code false} if the
     *         department already has a manager, or either the employee or the
     *         department does not belong to the company
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
        employee.setDepartment(department);
        return true;
    }

    // -----------------------------------------------------------------
    // Additional helper methods (optional for testing)
    // -----------------------------------------------------------------

    /**
     * Finds a department by its name.
     *
     * @param name the department name
     * @return the matching {@link Department} or {@code null} if not found
     */
    public Department findDepartmentByName(String name) {
        for (Department d : departments) {
            if (Objects.equals(d.getName(), name)) {
                return d;
            }
        }
        return null;
    }

    /**
     * Finds an employee by its identifier.
     *
     * @param id the employee identifier
     * @return the matching {@link Employee} or {@code null} if not found
     */
    public Employee findEmployeeById(String id) {
        for (Employee e : employees) {
            if (Objects.equals(e.getId(), id)) {
                return e;
            }
        }
        return null;
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    /** Department name – useful for testing and identification. */
    private String name;

    /** Offices belonging to the department. */
    private List<Office> offices;

    /** The office that serves as the headquarter (may be {@code null}). */
    private Office headquarter;

    /** Manager of the department (may be {@code null}). */
    private Employee manager;

    /** No‑argument constructor required by the task. */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
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

    /* --------------------------------------------------------------------- */
    /* Business methods                                                       */
    /* --------------------------------------------------------------------- */

    /**
     * Assigns the given office as the headquarter of this department.
     * The office must already be part of the department's office list and the
     * department must not already have a headquarter.
     *
     * @param office the office to become headquarter
     * @return {@code true} if the assignment succeeded,
     *         {@code false} if the office does not belong to the department
     *         or a headquarter is already set
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
     * @return {@code true} if a manager is set, {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Removes an employee from the department. If the employee is the current
     * manager, the manager reference is cleared.
     *
     * @param employee the employee to remove
     * @return {@code true} if the employee was the manager and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employee.equals(manager)) {
            manager = null;
            employee.setDepartment(null);
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from the department. If the office is the current
     * headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteOffice(Office office) {
        if (office == null) {
            return false;
        }
        boolean removed = offices.remove(office);
        if (removed && office.equals(headquarter)) {
            headquarter = null;
        }
        return removed;
    }

    /**
     * Adds a new office to the department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added, {@code false} if it already
     *         existed or is {@code null}
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
}

/**
 * Represents an office location.
 */
class Office {

    /** Office identifier – useful for testing. */
    private String id;

    /** No‑argument constructor required by the task. */
    public Office() {
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Optional: override equals / hashCode for proper list operations

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Office)) return false;
        Office other = (Office) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Represents an employee.
 */
class Employee {

    /** Unique employee identifier. */
    private String id;

    /** Human‑readable name of the employee. */
    private String name;

    /** The department this employee belongs to (may be {@code null}). */
    private Department department;

    /** No‑argument constructor required by the task. */
    public Employee() {
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    // Optional: override equals / hashCode for proper list operations

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof Employee)) return false;
        Employee other = (Employee) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}