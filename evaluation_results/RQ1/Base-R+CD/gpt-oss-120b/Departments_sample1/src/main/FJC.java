import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a company that contains departments and employees.
 */
 class Company {

    /** List of departments belonging to the company. */
    private List<Department> departments;

    /** List of all employees employed by the company. */
    private List<Employee> employees;

    /** No‑argument constructor. */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
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
     * @return {@code true} if the department was added; {@code false} if it
     *         already exists in the company
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
     * @return {@code true} if the employee was added; {@code false} if it
     *         already exists in the company
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
     * Deletes a department from the company. All offices belonging to the
     * department (including its headquarter) are removed, and every employee
     * that belongs to this department (including the manager) is also removed
     * from the company.
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was deleted;
     *         {@code false} otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove all employees that belong to this department
        Iterator<Employee> empIter = employees.iterator();
        while (empIter.hasNext()) {
            Employee emp = empIter.next();
            if (department.equals(emp.getDepartment())) {
                empIter.remove();
            }
        }

        // Clear offices (headquarter will be cleared automatically)
        department.getOffices().clear();
        department.setHeadquarter(null);
        department.setManager(null);

        // Finally remove the department itself
        departments.remove(department);
        return true;
    }

    /**
     * Returns a list of departments that do not have a manager assigned.
     *
     * @return a {@code List<Department>} containing all departments without a
     *         manager, or {@code null} if every department already has a manager
     */
    public List<Department> listDepartmentsWithoutManager() {
        List<Department> withoutManager = new ArrayList<>();
        for (Department d : departments) {
            if (!d.hasManager()) {
                withoutManager.add(d);
            }
        }
        return withoutManager.isEmpty() ? null : withoutManager;
    }

    /**
     * Assigns an employee as the manager of a specified department.
     * <p>
     * The employee and the department must both belong to this company and the
     * department must not already have a manager.
     *
     * @param employee   the employee to become manager
     * @param department the department that will receive the manager
     * @return {@code true} if the assignment succeeded; {@code false} if the
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
        if (department.hasManager()) {
            return false;
        }
        department.setManager(employee);
        employee.setDepartment(department);
        return true;
    }
}

/**
 * Represents a department inside a company.
 */
class Department {

    /** Offices that belong to this department. */
    private List<Office> offices;

    /** The office designated as headquarter (may be {@code null}). */
    private Office headquarter;

    /** The manager of this department (may be {@code null}). */
    private Employee manager;

    /** No‑argument constructor. */
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

    /* ==================== Business Operations ==================== */

    /**
     * Assigns an existing office of this department as its headquarter.
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
     * @return {@code true} if a manager is assigned; {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes the specified employee from this department. If the employee is
     * the current manager, the manager reference is cleared.
     *
     * @param employee the employee to delete
     * @return {@code true} if the employee was the manager and was removed;
     *         {@code false} if the employee was not the manager or {@code null}
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
     * Deletes the specified office from this department. If the office is the
     * current headquarter, the headquarter reference is cleared.
     *
     * @param office the office to delete
     * @return {@code true} if the office existed in the department and was
     *         removed; {@code false} otherwise
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
     * @return {@code true} if the office was added; {@code false} if it is
     *         {@code null} or already present
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
 * Represents an office location.
 */
class Office {

    /** A simple identifier for the office (e.g., address or name). */
    private String name;

    /** No‑argument constructor. */
    public Office() {
        this.name = "";
    }

    /**
     * Parameterized constructor.
     *
     * @param name the name or identifier of the office
     */
    public Office(String name) {
        this.name = name;
    }

    /* ==================== Getters & Setters ==================== */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

/**
 * Represents an employee of the company.
 */
class Employee {

    /** Employee's name. */
    private String name;

    /** The department this employee belongs to; may be {@code null}. */
    private Department department;

    /** No‑argument constructor. */
    public Employee() {
        this.name = "";
        this.department = null;
    }

    /**
     * Parameterized constructor.
     *
     * @param name the employee's name
     */
    public Employee(String name) {
        this.name = name;
        this.department = null;
    }

    /* ==================== Getters & Setters ==================== */

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
}