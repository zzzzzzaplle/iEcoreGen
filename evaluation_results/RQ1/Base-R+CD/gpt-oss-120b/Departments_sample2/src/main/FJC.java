import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** The list of departments belonging to this company. */
    private List<Department> departments = new ArrayList<>();

    /** The list of employees belonging to this company. */
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
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
    /* Business operations                                                    */
    /* --------------------------------------------------------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return {@code true} if the department was added; {@code false} if the
     *         department is {@code null} or already present in the company
     */
    public boolean addDepartment(Department department) {
        if (department == null) {
            return false;
        }
        if (departments.contains(department)) {
            return false;
        }
        departments.add(department);
        return true;
    }

    /**
     * Adds an employee to the company.
     *
     * @param employee the employee to add
     * @return {@code true} if the employee was added; {@code false} if the
     *         employee is {@code null} or already present in the company
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        if (employees.contains(employee)) {
            return false;
        }
        employees.add(employee);
        return true;
    }

    /**
     * Deletes a department from the company. All offices (including the
     * headquarters) belonging to the department are removed, and any employees
     * that are assigned to that department (including its manager) are also
     * removed from the company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed; {@code false}
     *         otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove employees that belong to this department
        List<Employee> toRemove = new ArrayList<>();
        for (Employee e : employees) {
            if (Objects.equals(e.getDepartment(), department)) {
                toRemove.add(e);
            }
        }
        employees.removeAll(toRemove);

        // Clear offices (the department itself holds the list)
        department.getOffices().clear();
        department.setHeadquarter(null);
        department.setManager(null);

        // Finally remove the department from the company
        departments.remove(department);
        return true;
    }

    /**
     * Returns a list of departments that do not have a manager assigned.
     *
     * @return a {@link List} of {@link Department} without a manager,
     *         or {@code null} if every department already has a manager
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

    /**
     * Assigns an employee as the manager of a given department.
     *
     * @param employee   the employee to become manager
     * @param department the department that will receive the manager
     * @return {@code true} if the assignment succeeded; {@code false} if the
     *         department already has a manager, or either the employee or the
     *         department does not belong to this company
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        if (department.hasManager()) {
            return false;
        }
        department.setManager(employee);
        employee.setDepartment(department);
        return true;
    }

    /**
     * Checks whether a specific department has a manager assigned.
     *
     * @param department the department to check
     * @return {@code true} if the department has a manager; {@code false}
     *         otherwise or if the department does not belong to the company
     */
    public boolean hasManager(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }
        return department.hasManager();
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    /** Offices belonging to this department. */
    private List<Office> offices = new ArrayList<>();

    /** The headquarter office of this department (must be one of {@link #offices}). */
    private Office headquarter;

    /** The manager of this department. */
    private Employee manager;

    /** No‑argument constructor. */
    public Department() {
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                   */
    /* --------------------------------------------------------------------- */

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
    /* Business operations                                                    */
    /* --------------------------------------------------------------------- */

    /**
     * Assigns an existing office of this department as its headquarter.
     *
     * @param office the office to be set as headquarter
     * @return {@code true} if the office belongs to this department and a headquarter
     *         has not already been set; {@code false} otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        if (headquarter != null) {
            // Headquarters already assigned
            return false;
        }
        if (!offices.contains(office)) {
            return false;
        }
        headquarter = office;
        return true;
    }

    /**
     * Checks whether this department already has a manager assigned.
     *
     * @return {@code true} if a manager is assigned; {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Removes an employee from this department. If the employee is the current
     * manager, the manager reference is cleared.
     *
     * @param employee the employee to remove
     * @return {@code true} if the employee was the manager and was removed;
     *         {@code false} if the employee was not the manager
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
     * Deletes an office from this department. If the office is the current
     * headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed and was removed; {@code false}
     *         otherwise
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
     * Adds an office to this department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added; {@code false} if {@code null}
     *         or already present
     */
    public boolean addOffice(Office office) {
        if (office == null) {
            return false;
        }
        if (offices.contains(office)) {
            return false;
        }
        offices.add(office);
        return true;
    }
}

/**
 * Represents an office belonging to a department.
 */
class Office {

    /** A simple identifier or name for the office. */
    private String name;

    /** No‑argument constructor. */
    public Office() {
    }

    /**
     * Constructs an office with the given name.
     *
     * @param name the name of the office
     */
    public Office(String name) {
        this.name = name;
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

    /* --------------------------------------------------------------------- */
    /* Equality helpers                                                       */
    /* --------------------------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;
        return Objects.equals(name, office.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

/**
 * Represents an employee of the company.
 */
class Employee {

    /** A simple identifier or name for the employee. */
    private String name;

    /** The department this employee belongs to (may be {@code null}). */
    private Department department;

    /** No‑argument constructor. */
    public Employee() {
    }

    /**
     * Constructs an employee with the given name.
     *
     * @param name the employee's name
     */
    public Employee(String name) {
        this.name = name;
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

   public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /* --------------------------------------------------------------------- */
    /* Equality helpers                                                       */
    /* --------------------------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;
        return Objects.equals(name, employee.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}