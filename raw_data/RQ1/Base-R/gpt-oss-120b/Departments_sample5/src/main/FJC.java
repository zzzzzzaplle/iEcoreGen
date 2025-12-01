import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a company that contains departments, offices and employees.
 */
 class Company {

    /** All departments belonging to the company. */
    private List<Department> departments;

    /** All employees belonging to the company (including managers). */
    private List<Employee> employees;

    /** Unparameterised constructor. */
    public Company() {
        this.departments = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    /** Getter for departments. */
    public List<Department> getDepartments() {
        return departments;
    }

    /** Setter for departments. */
    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    /** Getter for employees. */
    public List<Employee> getEmployees() {
        return employees;
    }

    /** Setter for employees. */
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    /**
     * Assigns an office as the headquarters for a department.
     * The office must belong to the department and the department must not already have a headquarters.
     *
     * @param department the department to which the headquarters will be assigned
     * @param office      the office that should become the headquarters
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
     */
    public boolean assignHeadquarter(Department department, Office office) {
        if (department == null || office == null) {
            return false;
        }
        // department must be part of this company
        if (!departments.contains(department)) {
            return false;
        }
        // office must belong to the department
        if (!department.getOffices().contains(office)) {
            return false;
        }
        // department must not already have a headquarters
        if (department.getHeadquarter() != null) {
            return false;
        }
        // perform assignment
        office.setHeadquarter(true);
        department.setHeadquarter(office);
        return true;
    }

    /**
     * Assigns an employee as manager of a specified department.
     * Both the employee and the department must belong to the company.
     *
     * @param department the department whose manager will be set
     * @param employee   the employee to become manager
     * @return {@code true} if the assignment succeeded, {@code false} otherwise
     */
    public boolean assignManager(Department department, Employee employee) {
        if (department == null || employee == null) {
            return false;
        }
        // both must belong to the company
        if (!departments.contains(department) || !employees.contains(employee)) {
            return false;
        }
        // department must not already have a manager
        if (department.getManager() != null) {
            return false;
        }
        // perform assignment
        department.setManager(employee);
        employee.setDepartment(department);
        return true;
    }

    /**
     * Deletes a department from the company.
     * All offices belonging to the department (including its headquarters) are removed.
     * All employees that belong to the department (including its manager) are also removed from the company.
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

        // Remove offices (the list will be garbage‑collected)
        department.getOffices().clear();
        department.setHeadquarter(null);

        // Remove employees that belong to this department
        List<Employee> toRemove = new ArrayList<>();
        for (Employee e : employees) {
            if (Objects.equals(e.getDepartment(), department)) {
                toRemove.add(e);
            }
        }
        employees.removeAll(toRemove);

        // Finally remove the department itself
        departments.remove(department);
        return true;
    }

    /**
     * Checks whether a department has a manager assigned.
     *
     * @param department the department to check
     * @return {@code true} if the department has a manager, {@code false} otherwise
     */
    public boolean hasManager(Department department) {
        if (department == null) {
            return false;
        }
        return department.getManager() != null;
    }

    /**
     * Returns a list of all departments that do not have a manager assigned.
     * If every department already has a manager, {@code null} is returned.
     *
     * @return an unmodifiable list of departments without a manager, or {@code null} if none exist
     */
    public List<Department> getDepartmentsWithoutManager() {
        List<Department> result = new ArrayList<>();
        for (Department d : departments) {
            if (d.getManager() == null) {
                result.add(d);
            }
        }
        return result.isEmpty() ? null : Collections.unmodifiableList(result);
    }

    /**
     * Utility method to add a department to the company.
     *
     * @param department the department to add
     */
    public void addDepartment(Department department) {
        if (department != null && !departments.contains(department)) {
            departments.add(department);
        }
    }

    /**
     * Utility method to add an employee to the company.
     *
     * @param employee the employee to add
     */
    public void addEmployee(Employee employee) {
        if (employee != null && !employees.contains(employee)) {
            employees.add(employee);
        }
    }
}

/**
 * Represents a department within a company.
 */
class Department {

    /** Name of the department. */
    private String name;

    /** Offices belonging to this department. */
    private List<Office> offices;

    /** The office designated as headquarters (may be {@code null}). */
    private Office headquarter;

    /** Manager of the department (may be {@code null}). */
    private Employee manager;

    /** Unparameterised constructor. */
    public Department() {
        this.offices = new ArrayList<>();
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for offices. */
    public List<Office> getOffices() {
        return offices;
    }

    /** Setter for offices. */
    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    /** Getter for headquarter. */
    public Office getHeadquarter() {
        return headquarter;
    }

    /** Setter for headquarter. */
    public void setHeadquarter(Office headquarter) {
        this.headquarter = headquarter;
    }

    /** Getter for manager. */
    public Employee getManager() {
        return manager;
    }

    /** Setter for manager. */
    public void setManager(Employee manager) {
        this.manager = manager;
    }

    /**
     * Adds an office to this department.
     *
     * @param office the office to add
     */
    public void addOffice(Office office) {
        if (office != null && !offices.contains(office)) {
            offices.add(office);
        }
    }
}

/**
 * Represents an office belonging to a department.
 */
class Office {

    /** Identifier of the office (e.g., address or code). */
    private String identifier;

    /** Flag indicating whether this office is the headquarters of its department. */
    private boolean headquarter;

    /** Unparameterised constructor. */
    public Office() {
        this.headquarter = false;
    }

    /** Getter for identifier. */
    public String getIdentifier() {
        return identifier;
    }

    /** Setter for identifier. */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /** Returns {@code true} if this office is a headquarters. */
    public boolean isHeadquarter() {
        return headquarter;
    }

    /** Sets the headquarters flag. */
    public void setHeadquarter(boolean headquarter) {
        this.headquarter = headquarter;
    }
}

/**
 * Represents an employee of the company.
 */
class Employee {

    /** Unique identifier of the employee (e.g., employee number). */
    private String employeeId;

    /** Name of the employee. */
    private String name;

    /** Department to which the employee belongs (may be {@code null}). */
    private Department department;

    /** Unparameterised constructor. */
    public Employee() {
    }

    /** Getter for employeeId. */
    public String getEmployeeId() {
        return employeeId;
    }

    /** Setter for employeeId. */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /** Getter for name. */
    public String getName() {
        return name;
    }

    /** Setter for name. */
    public void setName(String name) {
        this.name = name;
    }

    /** Getter for department. */
    public Department getDepartment() {
        return department;
    }

    /** Setter for department. */
    public void setDepartment(Department department) {
        this.department = department;
    }
}