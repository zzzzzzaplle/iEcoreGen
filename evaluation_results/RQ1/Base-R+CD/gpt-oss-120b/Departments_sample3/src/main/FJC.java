import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** List of departments belonging to the company. */
    private List<Department> departments;

    /** List of all employees working for the company. */
    private List<Employee> employees;

    /** Unparameterized constructor initializing empty collections. */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /* -------------------- Getters & Setters -------------------- */

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

    /* -------------------- Business Methods -------------------- */

    /**
     * Adds a department to the company if it does not already exist.
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
     * Adds an employee to the company if it does not already exist.
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
     * Deletes a department from the company together with its offices and any
     * employees that belong to it (including the manager). The department must
     * exist in the company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed, {@code false}
     *         otherwise
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

        // If there were other employees linked to the department, they could be
        // removed here as well. Since the model only defines a manager relationship,
        // we only remove the manager.

        // Offices are owned solely by the department; once the department is removed,
        // its offices become unreachable and will be garbage‑collected.
        department.clearOffices();

        return true;
    }

    /**
     * Assigns the given employee as manager of the specified department.
     * Both the employee and the department must belong to the company, and the
     * department must not already have a manager.
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
            return false; // employee or department not part of the company
        }
        if (department.getManager() != null) {
            return false; // department already has a manager
        }

        department.setManager(employee);
        return true;
    }

    /**
     * Returns a list of departments that do not have a manager assigned.
     * If every department already has a manager, {@code null} is returned.
     *
     * @return a list of departments without a manager, or {@code null} if none exist
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> withoutManager = new ArrayList<>();
        for (Department dept : departments) {
            if (!dept.hasManager()) {
                withoutManager.add(dept);
            }
        }
        return withoutManager.isEmpty() ? null : withoutManager;
    }
}

/**
 * Represents a department within a company. A department contains offices,
 * may have a designated headquarter office, and may have a manager.
 */
class Department {

    /** Offices belonging to this department. */
    private List<Office> offices;

    /** The office designated as headquarter (may be {@code null}). */
    private Office headquarter;

    /** The manager of the department (may be {@code null}). */
    private Employee manager;

    /** Unparameterized constructor initializing an empty list of offices. */
    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }

    /* -------------------- Getters & Setters -------------------- */

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

    /* -------------------- Business Methods -------------------- */

    /**
     * Assigns the given office as the headquarter of this department.
     * The office must already belong to this department and the department
     * must not already have a headquarter.
     *
     * @param office the office to become headquarter
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        if (!offices.contains(office)) {
            return false; // office does not belong to this department
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
     * Deletes an employee from the department. Currently the only employee
     * linked to a department is the manager. If the given employee is the manager,
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
        if (employee.equals(manager)) {
            manager = null;
            return true;
        }
        return false;
    }

    /**
     * Deletes an office from the department. If the office is currently set as
     * the headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed and was removed, {@code false}
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
     * @return {@code true} if the office was added, {@code false} if it was null
     *         or already present
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
     * Clears all offices from the department. This method is used when a
     * department is removed from the company.
     */
    public void clearOffices() {
        offices.clear();
        headquarter = null;
    }
}

/**
 * Represents an office. For simplicity it only contains an identifier.
 */
class Office {

    /** Unique identifier or name of the office. */
    private String name;

    /** Unparameterized constructor. */
    public Office() {
        this.name = "";
    }

    /** Constructor with name. */
    public Office(String name) {
        this.name = name;
    }

    /* -------------------- Getters & Setters -------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* -------------------- Equality helpers -------------------- */

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
 * Represents an employee. For simplicity it only contains an identifier.
 */
class Employee {

    /** Unique identifier or name of the employee. */
    private String name;

    /** Unparameterized constructor. */
    public Employee() {
        this.name = "";
    }

    /** Constructor with name. */
    public Employee(String name) {
        this.name = name;
    }

    /* -------------------- Getters & Setters -------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /* -------------------- Equality helpers -------------------- */

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