import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that owns departments and employees.
 */
 class Company {

    /** List of departments belonging to the company. */
    private List<Department> departments = new ArrayList<>();

    /** List of employees that work for the company. */
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor. */
    public Company() {
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

    public List<Department> getDepartments() {
        return Collections.unmodifiableList(departments);
    }

    public void setDepartments(List<Department> departments) {
        this.departments = new ArrayList<>(departments);
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }

    /* --------------------------------------------------------------------- */
    /* Business operations                                                    */
    /* --------------------------------------------------------------------- */

    /**
     * Adds a department to the company if it is not already present.
     *
     * @param department the department to add
     * @return {@code true} if the department was added, {@code false} otherwise
     */
    public boolean addDepartment(Department department) {
        if (department == null || departments.contains(department)) {
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
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }

    /**
     * Assigns the given employee as the manager of the given department.
     * Both the employee and the department must belong to the company and the
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
        // also make sure the employee is recorded in the department's staff list
        if (!department.getEmployees().contains(employee)) {
            department.addEmployee(employee);
        }
        return true;
    }

    /**
     * Deletes a department from the company. The department is removed together
     * with all its offices (including the headquarter) and all employees that
     * belong to the department (including its manager).
     *
     * @param department the department to delete
     * @return {@code true} if the department existed and was removed, {@code false}
     *         otherwise
     */
    public boolean deleteDepartment(Department department) {
        if (department == null || !departments.contains(department)) {
            return false;
        }

        // Remove employees that belong to the department from the company's employee list
        for (Employee e : new ArrayList<>(department.getEmployees())) {
            employees.remove(e);
        }

        // Finally remove the department itself
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
 * Represents a department inside a company.
 */
class Department {

    /** Offices that belong to this department. */
    private List<Office> offices = new ArrayList<>();

    /** The headquarter office (must be one of the offices). */
    private Office headquarter;

    /** Manager of the department. */
    private Employee manager;

    /** Employees that work in this department (including the manager). */
    private List<Employee> employees = new ArrayList<>();

    /** No‑argument constructor. */
    public Department() {
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

    public List<Office> getOffices() {
        return Collections.unmodifiableList(offices);
    }

    public void setOffices(List<Office> offices) {
        this.offices = new ArrayList<>(offices);
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

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = new ArrayList<>(employees);
    }

    /* --------------------------------------------------------------------- */
    /* Business operations                                                    */
    /* --------------------------------------------------------------------- */

    /**
     * Assigns the given office as the headquarter of this department.
     * The office must already belong to the department and the department must
     * not already have a headquarter.
     *
     * @param office the office to become headquarter
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
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
     * Checks whether this department has a manager assigned.
     *
     * @return {@code true} if a manager is present, {@code false} otherwise
     */
    public boolean hasManager() {
        return manager != null;
    }

    /**
     * Deletes an employee from this department. If the employee is the current
     * manager, the manager reference is cleared.
     *
     * @param employee the employee to delete
     * @return {@code true} if the employee existed and was removed, {@code false}
     *         otherwise
     */
    public boolean deleteEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        boolean removed = employees.remove(employee);
        if (removed && employee.equals(manager)) {
            manager = null;
        }
        return removed;
    }

    /**
     * Deletes an office from this department. If the office is the current
     * headquarter, the headquarter reference is cleared.
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
        if (office == null || offices.contains(office)) {
            return false;
        }
        return offices.add(office);
    }

    /**
     * Adds an employee to this department's staff list.
     *
     * @param employee the employee to add
     * @return {@code true} if the employee was added, {@code false} if null or
     *         already present
     */
    public boolean addEmployee(Employee employee) {
        if (employee == null || employees.contains(employee)) {
            return false;
        }
        return employees.add(employee);
    }
}

/**
 * Represents an office location.
 */
class Office {

    private String address;
    private String city;

    /** No‑argument constructor. */
    public Office() {
    }

    /** Constructor with fields (optional convenience). */
    public Office(String address, String city) {
        this.address = address;
        this.city = city;
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /* --------------------------------------------------------------------- */
    /* Equality helpers                                                       */
    /* --------------------------------------------------------------------- */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;
        return Objects.equals(address, office.address) &&
               Objects.equals(city, office.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, city);
    }
}

/**
 * Represents an employee.
 */
class Employee {

    private String employeeId;
    private String name;

    /** No‑argument constructor. */
    public Employee() {
    }

    /** Constructor with fields (optional convenience). */
    public Employee(String employeeId, String name) {
        this.employeeId = employeeId;
        this.name = name;
    }

    /* --------------------------------------------------------------------- */
    /* Getters and Setters                                                    */
    /* --------------------------------------------------------------------- */

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

    /* --------------------------------------------------------------------- */
    /* Equality helpers                                                       */
    /* --------------------------------------------------------------------- */

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