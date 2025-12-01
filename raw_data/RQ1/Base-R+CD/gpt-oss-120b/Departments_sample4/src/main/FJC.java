import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** List of departments belonging to the company. */
    private List<Department> departments;

    /** List of all employees that work for the company. */
    private List<Employee> employees;

    /** Unparameterized constructor. */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees   = new ArrayList<>();
    }

    /** -------------------- Getters & Setters -------------------- */

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

    /** -------------------- Business Methods -------------------- */

    /**
     * Adds a department to the company.
     *
     * @param department the department to add
     * @return {@code true} if the department was added, {@code false} if the
     *         department is {@code null} or already present
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
     * @return {@code true} if the employee was added, {@code false} if the
     *         employee is {@code null} or already present
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
     * Deletes a department from the company together with its offices and
     * removes the manager (if any) from the company's employee list.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed,
     *         {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove the department from the company
        departments.remove(department);

        // Remove the manager of the department from the company's employee list
        Employee manager = department.getManager();
        if (manager != null) {
            employees.remove(manager);
        }

        // All offices are owned exclusively by the department, so removing the
        // department automatically discards them. No extra action required.

        return true;
    }

    /**
     * Returns a list of departments that do not have a manager assigned.
     *
     * @return a {@link List} of departments without a manager, or {@code null}
     *         if every department already has a manager
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
     * @return {@code true} if the assignment succeeded, {@code false} if the
     *         employee or department does not belong to the company, or if
     *         the department already has a manager
     */
    public boolean assignManager(Employee employee, Department department) {
        if (employee == null || department == null) {
            return false;
        }
        if (!employees.contains(employee) || !departments.contains(department)) {
            return false;
        }
        if (department.getManager() != null) {
            return false; // already has a manager
        }
        department.setManager(employee);
        return true;
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    /** Offices belonging to this department. */
    private List<Office> offices;

    /** The office that is designated as headquarter (may be null). */
    private Office headquarter;

    /** The manager of the department (may be null). */
    private Employee manager;

    /** Unparameterized constructor. */
    public Department() {
        this.offices = new ArrayList<>();
        this.headquarter = null;
        this.manager = null;
    }

    /** -------------------- Getters & Setters -------------------- */

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

    /** -------------------- Business Methods -------------------- */

    /**
     * Assigns the given office as the headquarter of this department.
     *
     * @param office the office to be set as headquarter
     * @return {@code true} if the office belongs to this department and the
     *         department does not already have a headquarter; {@code false}
     *         otherwise
     */
    public boolean assignOfficeAsHeadquarter(Office office) {
        if (office == null) {
            return false;
        }
        if (!offices.contains(office)) {
            return false; // office does not belong to this department
        }
        if (headquarter != null) {
            return false; // already has a headquarter
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
     * Deletes the given employee from the department. The only employee tracked
     * by a department is its manager; therefore this method removes the manager
     * if the supplied employee matches the current manager.
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
     * Deletes the given office from the department. If the office is the current
     * headquarter, the headquarter reference is cleared.
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
        if (removed && office.equals(headquarter)) {
            headquarter = null;
        }
        return removed;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     * @return {@code true} if the office was added, {@code false} if it is
     *         {@code null} or already present
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

    /** Simple identifier for the office (e.g., address or name). */
    private String name;

    /** Unparameterized constructor. */
    public Office() {
        this.name = "";
    }

    /** Parameterized constructor for convenience. */
    public Office(String name) {
        this.name = name;
    }

    /** -------------------- Getter & Setter -------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** -------------------- Equality -------------------- */

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

    /** Simple identifier for the employee (e.g., employee ID or name). */
    private String name;

    /** Unparameterized constructor. */
    public Employee() {
        this.name = "";
    }

    /** Parameterized constructor for convenience. */
    public Employee(String name) {
        this.name = name;
    }

    /** -------------------- Getter & Setter -------------------- */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** -------------------- Equality -------------------- */

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